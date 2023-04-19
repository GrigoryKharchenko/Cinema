package kinopoisk.cinema.presentation.screen.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kinopoisk.cinema.di.IoDispatcher
import kinopoisk.cinema.domain.repository.GalleryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GalleryViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _galleryFlow = MutableStateFlow<GalleryUiState>(GalleryUiState.Loading)
    val galleryFlow = _galleryFlow.asStateFlow()

    fun getGallery(id: Int, type: String) {
        viewModelScope.launch(ioDispatcher) {
            galleryRepository.getSizeGallery(id, type)
                .onSuccess { photos ->
                    _galleryFlow.emit(GalleryUiState.Success(photos))
                }
                .onFailure {
                    _galleryFlow.emit(GalleryUiState.Error)
                }
        }
    }
}
