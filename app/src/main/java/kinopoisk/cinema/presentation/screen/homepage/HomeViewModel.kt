package kinopoisk.cinema.presentation.screen.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kinopoisk.cinema.di.IoDispatcher
import kinopoisk.cinema.domain.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiModelFLow = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiModelFlow = _uiModelFLow.asStateFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                categoryRepository.getCategories()
            }.onSuccess { categories ->
                _uiModelFLow.emit(HomeUiState.Success(categories))
            }.onFailure {
                _uiModelFLow.emit(HomeUiState.Error)
            }
        }
    }
}
