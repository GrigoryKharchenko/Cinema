package kinopoisk.cinema.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kinopoisk.cinema.presentation.main.MainFragment
import kinopoisk.cinema.presentation.screen.filmdetail.FilmDetailFragment
import kinopoisk.cinema.presentation.screen.films.FilmsFragment
import kinopoisk.cinema.presentation.screen.homepage.HomeFragment
import kinopoisk.cinema.presentation.screen.profilepage.ProfileFragment
import kinopoisk.cinema.presentation.screen.searchpage.SearchFragment
import kinopoisk.cinema.presentation.screen.welcome.WelcomeFragment

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun bindWelcomeFragment(): WelcomeFragment

    @ContributesAndroidInjector
    fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    fun bindProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    fun bindSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    fun bindMainFragment(): MainFragment

    @ContributesAndroidInjector
    fun bindFilmDetailFragment(): FilmDetailFragment

    @ContributesAndroidInjector
    fun bindFilmsFragment(): FilmsFragment
}
