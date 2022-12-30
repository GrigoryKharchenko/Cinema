package kinopoisk.cinema.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import kinopoisk.cinema.CinemaApp
import kinopoisk.cinema.di.module.ActivityModule
import kinopoisk.cinema.di.module.DispatcherModule
import kinopoisk.cinema.di.module.FragmentModule
import kinopoisk.cinema.di.module.RepositoryModule
import kinopoisk.cinema.di.module.RetrofitModule
import kinopoisk.cinema.di.module.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        FragmentModule::class,
        RetrofitModule::class,
        RepositoryModule::class,
        DispatcherModule::class
    ]
)
interface AppComponent : AndroidInjector<CinemaApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
