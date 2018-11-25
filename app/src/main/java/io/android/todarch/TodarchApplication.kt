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
package io.android.todarch

import android.os.StrictMode
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.android.todarch.core.BuildConfig
import io.android.todarch.core.dagger.CoreComponent
import io.android.todarch.core.dagger.DaggerCoreComponent
import io.android.todarch.dagger.DaggerSingletonComponent

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 14.10.2018.
 */
class TodarchApplication : DaggerApplication() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent
            .builder()
            .build()
    }

    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build()
            )
        }
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out TodarchApplication> {
        return DaggerSingletonComponent.builder()
            .coreComponent(coreComponent)
            .application(this)
            .create(this)
    }
}