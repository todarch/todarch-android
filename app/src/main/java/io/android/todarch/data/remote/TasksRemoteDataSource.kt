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
package io.android.todarch.data.remote

import io.android.todarch.core.data.api.Result
import io.android.todarch.core.data.api.TodarchService
import io.android.todarch.core.data.model.Task
import io.android.todarch.core.data.model.response.ResponseTask
import io.android.todarch.core.util.safeApiCall
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 19.11.2018.
 */
@Singleton
class TasksRemoteDataSource @Inject constructor(private val service: TodarchService) {
    /**
     * adds a [Task]
     */
    suspend fun addTask(task: Task) = safeApiCall(
        call = { launchAddTask(task) },
        errorMessage = "Adding a new task failed"
    )

    private suspend fun launchAddTask(task: Task): Result<ResponseTask> {
        val response = service.addTask(task).await()
        if (response.errorCode.isNullOrEmpty()) {
            return Result.Success(response)
        }
        return Result.Error(
            IOException("Error adding new task ${response.errorCode} ${response.errorMessage}")
        )
    }

    /**
     * gets all list of [Task] of the current user
     */
    suspend fun getTasks() = safeApiCall(
        call = { launchGetTasks() },
        errorMessage = "Adding a new task failed"
    )

    private suspend fun launchGetTasks(): Result<List<Task>> {
        val tasks = service.getTasks().await()
        tasks?.run {
            if (isEmpty()) {
                return Result.Empty()
            }
            return Result.Success(this)
        }
        return Result.Error(IOException("Error adding new task"))
    }
}