package io.android.todarch.user.dagger

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.android.todarch.core.dagger.ScopeFragment
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
}