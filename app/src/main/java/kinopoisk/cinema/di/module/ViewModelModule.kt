package kinopoisk.cinema.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kinopoisk.cinema.di.ViewModelKey
import kinopoisk.cinema.presentation.screen.filmdetail.FilmDetailViewModel
import kinopoisk.cinema.presentation.screen.homepage.HomeViewModel
import kinopoisk.cinema.presentation.screen.profilepage.ProfileViewModel
import kinopoisk.cinema.presentation.screen.searchpage.SearchViewModel

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilmDetailViewModel::class)
    fun bindFilmDetailViewModel(viewModel: FilmDetailViewModel): ViewModel

}
