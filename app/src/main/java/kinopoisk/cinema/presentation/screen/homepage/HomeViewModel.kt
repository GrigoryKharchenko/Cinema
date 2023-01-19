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

    // TODO: а если у тебя этот метод апишки будет в нескольких местах вызываться ты везде будешь runCatching тыкать? может это в общую реализацию вынести?
    private fun getCategories() {
        // TODO: в чем суть сего действа если ты даже название оставил как у стандартного диспатчера?
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
