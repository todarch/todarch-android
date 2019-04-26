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
package io.android.todarch.data

import androidx.annotation.WorkerThread
import io.android.todarch.core.data.api.Result
import io.android.todarch.core.data.model.Task
import io.android.todarch.core.data.model.response.ResponseTask
import io.android.todarch.data.local.TasksDataSource
import io.android.todarch.data.remote.TasksRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 1.12.2018.
 */
@Singleton
class TasksRepository @Inject constructor(
        private val localDataSource: TasksDataSource,
        private val remoteDataSource: TasksRemoteDataSource
) {
    /**
     * This variable has public visibility so it can be accessed from tests.
     */
    private var cachedTasks: LinkedHashMap<String, Task> = LinkedHashMap()

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    private var cacheIsDirty = true

    /**
     * Gets tasks from cache, local data source or remote data source, whichever is
     * available first.
     */
    @WorkerThread
    suspend fun getAllTasks(): Result<List<Task>> {
        // Respond immediately with cache if available and not dirty
        if (cachedTasks.isNotEmpty() && !cacheIsDirty) {
            return Result.Success<List<Task>>(ArrayList(cachedTasks.values))
        }

        return if (cacheIsDirty) {
            getTasksFromRemoteDataSource()
        } else {
            getTasksFromLocalDataSource()
        }
    }

    fun refreshTasks() {
        cacheIsDirty = true
    }

    @WorkerThread
    suspend fun saveTask(task: Task) {
        // Do in memory cache update to keep the app UI up to date
        cacheAndPerform(task) { cachedTask ->
            val result: Result<ResponseTask> = remoteDataSource.addTask(cachedTask)
            var isTaskSavedToDatabase = false
            if (result is Result.Success) {
                result.data.id?.let { it ->
                    isTaskSavedToDatabase = refreshIdAndSaveToLocalDatabase(cachedTask, it)
                }
            }

            if (!isTaskSavedToDatabase) {
                // TODO save task to different table that holds tasks which couldn't send to server
            }
        }
    }

    @WorkerThread
    private suspend fun refreshIdAndSaveToLocalDatabase(task: Task, originalId: String): Boolean {
        task.id = originalId
        localDataSource.saveTask(task)
        return true
    }

    @WorkerThread
    private suspend fun getTasksFromRemoteDataSource(): Result<List<Task>> {
        val result = remoteDataSource.getTasks()
        if (result is Result.Success) {
            val tasks = result.data
            refreshCache(tasks)
            refreshLocalDataSource(tasks)
        }
        return result
    }

    @WorkerThread
    private suspend fun getTasksFromLocalDataSource(): Result<List<Task>> {
        // Query the local storage if available. If not, query the network.
        val result = localDataSource.getAllTasks()
        return if (result is Result.Success) {
            refreshCache(result.data)
            result
        } else {
            getTasksFromRemoteDataSource()
        }
    }

    @WorkerThread
    private suspend fun refreshLocalDataSource(tasks: List<Task>) {
        localDataSource.deleteAllTasks()
        for (task in tasks) {
            localDataSource.saveTask(task)
        }
    }

    @WorkerThread
    private fun refreshCache(tasks: List<Task>) {
        cachedTasks.clear()
        tasks.forEach {
            cacheAndPerform(it) {}
        }
        cacheIsDirty = false
    }

    @WorkerThread
    private inline fun cacheAndPerform(task: Task, perform: (Task) -> Unit) {
        val cachedTask = Task(
                task.id,
                task.title,
                task.description,
                task.completed,
                task.priority,
                task.status,
                task.tags,
                task.timeNeededInMin,
                task.createdDateInEpoch,
                task.doneDateInEpoch
        )
        cachedTasks[cachedTask.id] = cachedTask
        perform(cachedTask)
    }

    @WorkerThread
    suspend fun deleteAllTasks() {
        localDataSource.deleteAllTasks()
        cachedTasks.clear()
    }
}