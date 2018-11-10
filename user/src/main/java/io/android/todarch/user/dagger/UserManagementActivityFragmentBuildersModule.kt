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
package io.android.todarch.user.dagger

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.android.todarch.core.dagger.ScopeFragment
import io.android.todarch.user.RegisterFragment
import io.android.todarch.user.ui.LoginFragment

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 3.11.2018.
 */
@Module
abstract class UserManagementActivityFragmentBuildersModule {
    @ScopeFragment
    @ContributesAndroidInjector
    internal abstract fun bindLoginFragment(): LoginFragment

    @ScopeFragment
    @ContributesAndroidInjector
    internal abstract fun bindRegisterFragment(): RegisterFragment
}