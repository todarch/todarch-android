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
import io.android.todarch.TodarchApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 21.10.2018.
 */
@Module(includes = [ViewModelModule::class])
class SingletonModule {

    @Provides
    @Singleton
    internal fun provideContext(application: TodarchApplication): Context {
        return application
    }
}