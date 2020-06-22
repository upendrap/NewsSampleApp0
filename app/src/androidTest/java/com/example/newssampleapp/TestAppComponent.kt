package com.example.newssampleapp

import android.content.Context
import com.example.newssampleapp.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [AndroidSupportInjectionModule::class,
        ActivityModule::class,
        FakeRepositoryModule::class,
        NetworkModule::class]
)
@Singleton
interface TestAppComponent : AppComponent {
    @Component.Builder
    interface Builder {
        fun build(): TestAppComponent

        @BindsInstance
        fun bindContext(context: Context): Builder
    }


    fun inject(app: TestApp)
}

