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
package io.android.todarch.core.dagger

import dagger.Module
import dagger.Provides
import io.android.todarch.core.data.CoroutinesContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.android.Main
import javax.inject.Singleton

/**
 * Provide [CoroutinesContextProvider] to this app's components.
 *
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 18.11.2018.
 */
@Module
class CoroutinesContextProviderModule {

    @Singleton
    @Provides
    fun provideCoroutinesContextProvider(): CoroutinesContextProvider {
        return CoroutinesContextProvider(Dispatchers.Main, Dispatchers.Default)
    }
}