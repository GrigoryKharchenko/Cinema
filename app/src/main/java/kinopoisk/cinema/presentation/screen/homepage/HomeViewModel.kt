package kinopoisk.cinema.presentation.screen.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kinopoisk.cinema.di.IoDispatcher
import kinopoisk.cinema.domain.reposittory.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiStateFLow = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiStateFlow = _uiStateFLow.asStateFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                categoryRepository.getCategories()
            }.onSuccess { categories ->
                _uiStateFLow.emit(HomeUiState.Success(categories))
            }.onFailure {
                _uiStateFLow.emit(HomeUiState.Error)
            }
        }
    }
}
