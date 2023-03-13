package kinopoisk.cinema.presentation.screen.staff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kinopoisk.cinema.domain.repository.StaffRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StaffViewModel @AssistedInject constructor(
    private val detailFilmRepository: StaffRepository,
    @Assisted private val typeTitleStaff: TypeTitleStaff
) : ViewModel() {

    init {
        getStaff()
    }

    private val _staffUiStateFlow = MutableStateFlow<StaffUiState>(StaffUiState.Loading)
    val staffUiStateFlow = _staffUiStateFlow.asStateFlow()

    private fun getStaff() {
        viewModelScope.launch {
            runCatching {
                detailFilmRepository.getStuff(typeTitleStaff)
            }.onSuccess {
                _staffUiStateFlow.emit(StaffUiState.Success(it))
            }.onFailure {
                _staffUiStateFlow.emit(StaffUiState.Error)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(typeTitleStaff: TypeTitleStaff): StaffViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            typeTitleStaff: TypeTitleStaff
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(typeTitleStaff) as T
            }
        }
    }
}
