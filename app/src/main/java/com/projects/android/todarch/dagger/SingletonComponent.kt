package com.projects.android.todarch.dagger

import com.projects.android.todarch.TodarchApplication
import com.projects.android.todarch.core.dagger.CoreComponent
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
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
        ActivityBuilder::class
    ],
    dependencies = [CoreComponent::class]
)
abstract class SingletonComponent : AndroidInjector<TodarchApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TodarchApplication>() {
        abstract fun coreComponent(module: CoreComponent): Builder
    }
}