package kinopoisk.cinema.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kinopoisk.cinema.di.ViewModelKey
import kinopoisk.cinema.presentation.screen.gallery.GalleryViewModel
import kinopoisk.cinema.presentation.screen.homepage.HomeViewModel
import kinopoisk.cinema.presentation.screen.period.PeriodViewModel
import kinopoisk.cinema.presentation.screen.profilepage.ProfileViewModel
import kinopoisk.cinema.presentation.screen.searchpage.SearchViewModel
import kinopoisk.cinema.presentation.screen.searchsettings.SearchSettingsViewModel
import kinopoisk.cinema.presentation.screen.season.SeasonViewModel

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
    @ViewModelKey(SearchSettingsViewModel::class)
    fun bindSearchSettingsViewModel(viewModel: SearchSettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GalleryViewModel::class)
    fun bindGalleryViewModel(viewModel: GalleryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PeriodViewModel::class)
    fun bindPeriodViewModel(viewModel: PeriodViewModel): ViewModel
}
