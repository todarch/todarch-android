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
package io.android.todarch.user.ui

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.android.todarch.core.data.CoroutinesContextProvider
import io.android.todarch.core.data.api.Result
import io.android.todarch.core.util.event.Event
import io.android.todarch.user.R
import io.android.todarch.user.data.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 3.11.2018.
 */
class LoginViewModel @Inject constructor(
    private val contextProvider: CoroutinesContextProvider,
    private val userRepository: UserRepository
) : ViewModel(), CoroutineScope {

    private var currentJob: Job? = null

    private val _uiState = MutableLiveData<LoginUIModel>()
    val uIState: LiveData<LoginUIModel>
        get() = _uiState

    override val coroutineContext: CoroutineContext
        get() = contextProvider.main

    @UiThread
    fun login(email: String, password: String) {
        // only allow one login at a time
        if (currentJob?.isActive == true) {
            return
        }
        currentJob = launchLogin(email, password)
    }

    private fun launchLogin(email: String, password: String) = launch {
        emitUiState(showProgress = true)

        val result = withContext(contextProvider.io) {
            userRepository.login(email, password)
        }

        if (result is Result.Success) {
            emitUiState(showSuccess = Event(R.string.success_register))
        } else {
            emitUiState(showError = Event(R.string.error_login))
        }
    }

    @UiThread
    private fun emitUiState(
        showProgress: Boolean = false,
        showError: Event<Int>? = null,
        showSuccess: Event<Int>? = null
    ) {
        val uiModel = LoginUIModel(showProgress, showError, showSuccess)
        _uiState.value = uiModel
    }

    override fun onCleared() {
        super.onCleared()
        currentJob?.cancel()
    }
}

/**
 * UI model for [LoginFragment]
 */
data class LoginUIModel(
    val showProgress: Boolean,
    val showError: Event<Int>?,
    val showSuccess: Event<Int>?
)