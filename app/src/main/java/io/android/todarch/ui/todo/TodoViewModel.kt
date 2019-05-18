/*
 * Copyright 2018 Todarch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.android.todarch.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.android.todarch.R
import io.android.todarch.core.data.CoroutinesContextProvider
import io.android.todarch.core.data.Session
import io.android.todarch.core.data.api.Result
import io.android.todarch.core.data.model.Task
import io.android.todarch.core.util.event.Event
import io.android.todarch.data.TasksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 21.10.2018.
 */
class TodoViewModel @Inject constructor(
	private val contextProvider: CoroutinesContextProvider,
	private val session: Session,
	private val tasksRepository: TasksRepository
) : ViewModel(), CoroutineScope {

	private val currentJob = SupervisorJob()

	private val _uiState = MutableLiveData<TodoUIModel>()
	val uIState: LiveData<TodoUIModel>
		get() = _uiState

	override val coroutineContext: CoroutineContext
		get() = contextProvider.main + currentJob

	init {
		loadTasks(false)
	}

	fun isNotLoggedIn(): Boolean = !session.isLoggedIn

	fun loadTasks(forceUpdate: Boolean) {
		if (forceUpdate) {
			tasksRepository.refreshTasks()
		}
		getAllTasks()
	}

	fun saveTask(title: String, description: String, tags: List<String>) = launch(contextProvider.io) {
		tasksRepository.saveTask(Task(System.currentTimeMillis().toString(), title, description, tags = tags))
		loadTasks(false)
		emitUiState(taskUpdated = Event(true))
	}

	private fun getAllTasks() = launch(contextProvider.io) {
		emitUiState(showProgress = true)

		when (val result = tasksRepository.getAllTasks()) {
			is Result.Success -> emitUiState(showSuccess = Event(result.data))
			is Result.Empty -> emitUiState(showEmpty = Event(R.string.empty_no_data_tasks))
			else -> emitUiState(showError = Event(R.string.error_getting_tasks))
		}
	}

	private fun emitUiState(
		showProgress: Boolean = false,
		showError: Event<Int>? = null,
		showEmpty: Event<Int>? = null,
		showSuccess: Event<List<Task>>? = null,
		taskUpdated: Event<Boolean>? = null
	) = launch(contextProvider.main) {
		val uiModel = TodoUIModel(showProgress, showError, showEmpty, showSuccess, taskUpdated)
		_uiState.value = uiModel
	}

	override fun onCleared() {
		super.onCleared()
		coroutineContext.cancelChildren()
	}
}

/**
 * UI model for [TodoListFragment]
 */
data class TodoUIModel(
	val showProgress: Boolean,
	val showError: Event<Int>?,
	val showEmpty: Event<Int>?,
	val showSuccess: Event<List<Task>>?,
	val taskUpdated: Event<Boolean>?
)