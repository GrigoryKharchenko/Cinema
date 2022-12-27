package kinopoisk.cinema.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kinopoisk.cinema.presentation.screen.welcome.WelcomeFragment

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun bindWelcomeFragment(): WelcomeFragment
}
