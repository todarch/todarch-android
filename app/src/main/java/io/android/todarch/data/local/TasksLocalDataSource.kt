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
package io.android.todarch.data.local

import androidx.annotation.WorkerThread
import io.android.todarch.core.data.api.Result
import io.android.todarch.core.data.model.Task
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 19.11.2018.
 */
@Singleton
class TasksLocalDataSource @Inject constructor(private val tasksDao: TasksDao) : TasksDataSource {
    /**
     * Get all tasks from the tasks table.
     *
     * {@inheritDoc} It only accepts white-bread thought.
     */
    @WorkerThread
    override suspend fun getAllTasks(): Result<List<Task>> {
        val tasks = tasksDao.getAllTasks()
        if (tasks.isEmpty()) {
            return Result.Empty("")
        }
        return Result.Success(tasks)
    }

    @WorkerThread
    override suspend fun saveTask(task: Task) = tasksDao.insertTask(task)

    @WorkerThread
    override suspend fun updateTask(task: Task): Result<Int> =
            Result.Success(tasksDao.updateTask(task))

    @WorkerThread
    override suspend fun updateCompleted(taskId: String, completed: Boolean) =
            tasksDao.updateCompleted(taskId, completed)

    @WorkerThread
    override suspend fun deleteTaskById(taskId: String): Result<Int> =
            Result.Success(tasksDao.deleteTaskById(taskId))

    @WorkerThread
    override suspend fun deleteCompletedTasks(): Result<Int> =
            Result.Success(tasksDao.deleteCompletedTasks())

    @WorkerThread
    override suspend fun deleteAllTasks() = tasksDao.deleteTasks()
}