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
package com.projects.android.todarch.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.android.todarch.core.dagger.TodarchViewModelFactory
import com.projects.android.todarch.core.dagger.ViewModelKey
import com.projects.android.todarch.ui.todo.TodoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 21.10.2018.
 */
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TodoViewModel::class)
    internal abstract fun bindTodoViewModel(todoViewModel: TodoViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: TodarchViewModelFactory): ViewModelProvider.Factory
}