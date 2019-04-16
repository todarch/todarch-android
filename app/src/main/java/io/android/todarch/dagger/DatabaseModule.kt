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
package io.android.todarch.dagger

import android.content.Context
import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import io.android.todarch.core.data.Session
import io.android.todarch.data.database.AppDatabase
import io.android.todarch.data.local.TasksDataSource
import io.android.todarch.data.local.TasksLocalDataSource
import io.android.todarch.user.data.UserLocalDataSource
import javax.inject.Singleton

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 23.11.2018.
 */
@Module
class DatabaseModule {

    @Singleton
    @Provides
    internal fun provideUserLocalDataSource(
        @NonNull session: Session,
        @NonNull database: AppDatabase
    ): UserLocalDataSource = UserLocalDataSource(session, database.userDao())

    @Singleton
    @Provides
    internal fun provideTasksDataSource(@NonNull database: AppDatabase): TasksDataSource =
        TasksLocalDataSource(database.tasksDao())

    @Singleton
    @Provides
    internal fun provideDatabase(@NonNull context: Context): AppDatabase = AppDatabase.getInstance(context)
}