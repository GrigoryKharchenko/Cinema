package kinopoisk.cinema.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kinopoisk.cinema.presentation.main.MainActivity

@Module
interface ActivityModule {

    @ContributesAndroidInjector
    fun provideMainActivity(): MainActivity
}
