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

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import io.android.todarch.TodarchApplication
import io.android.todarch.core.dagger.ApiModule
import io.android.todarch.core.dagger.CoreComponent
import io.android.todarch.core.dagger.CoroutinesContextProviderModule
import io.android.todarch.core.dagger.SharedPreferencesModule
import javax.inject.Singleton

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 22.10.2018.
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        SingletonModule::class,
        CoroutinesContextProviderModule::class,
        SharedPreferencesModule::class,
        ApiModule::class,
        ActivityBuilderModule::class,
        DatabaseModule::class
    ],
    dependencies = [CoreComponent::class]
)
abstract class SingletonComponent : AndroidInjector<TodarchApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TodarchApplication>() {

        abstract override fun build(): SingletonComponent

        abstract fun coreComponent(module: CoreComponent): Builder

        @BindsInstance
        abstract fun application(application: Application): Builder
    }
}