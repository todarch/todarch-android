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
package io.android.todarch.core.data.api

import io.android.todarch.core.data.model.Task
import io.android.todarch.core.data.model.response.ResponseTask
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 11.11.2018.
 */
interface TodarchService {

    @POST("td/api/todos")
    fun addTask(@Body task: Task): Deferred<ResponseTask>

    @GET("td/api/todos")
    fun getTasks(): Deferred<List<Task>?>
}