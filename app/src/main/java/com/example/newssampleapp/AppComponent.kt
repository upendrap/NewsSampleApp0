package com.example.newssampleapp

import android.content.Context
import com.example.newssampleapp.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [AndroidSupportInjectionModule::class,
        ActivityModule::class,
        RepositoryModule::class,
        NetworkModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun bindContext(context: Context): Builder
    }

    fun inject(app: App)
}