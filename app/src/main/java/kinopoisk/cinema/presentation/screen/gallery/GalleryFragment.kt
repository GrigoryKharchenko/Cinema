package kinopoisk.cinema.presentation.screen.gallery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kinopoisk.cinema.databinding.FragmentGalleryBinding
import kinopoisk.cinema.di.ViewModelFactory
import kinopoisk.cinema.extension.launchWhenStarted
import kinopoisk.cinema.presentation.screen.filmdetail.FilmDetailFragment
import javax.inject.Inject

class GalleryFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var defaultViewModelFactory: ViewModelFactory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private val argument by lazy {
        requireNotNull(arguments?.getInt(FilmDetailFragment.KEY_FILM))
    }

    private val adapter = TypeSizePhotoAdapter()

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelFactory)[GalleryViewModel::class.java]
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        with(binding) {
            toolBar.setNavigationOnClickListener { goBack() }
            rvPhotos.adapter = adapter
            filterStill.isChecked = true
            filterConcept.setOnClickListener {
                viewModel.getGallery(argument, TypeImage.CONCEPT.name)
            }
            filterStill.setOnClickListener {
                viewModel.getGallery(argument, TypeImage.STILL.name)
            }
            filterShooting.setOnClickListener {
                viewModel.getGallery(argument, TypeImage.SHOOTING.name)
            }
            filterPoster.setOnClickListener {
                viewModel.getGallery(argument, TypeImage.POSTER.name)
            }
            filterFanArt.setOnClickListener {
                viewModel.getGallery(argument, TypeImage.FAN_ART.name)
            }
            filterPromo.setOnClickListener {
                viewModel.getGallery(argument, TypeImage.PROMO.name)
            }
            filterWallpaper.setOnClickListener {
                viewModel.getGallery(argument, TypeImage.WALLPAPER.name)
            }
            filterCover.setOnClickListener {
                viewModel.getGallery(argument, TypeImage.COVER.name)
            }
            filterScreenshot.setOnClickListener {
                viewModel.getGallery(argument, TypeImage.SCREENSHOT.name)
            }
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            getGallery(argument, TypeImage.STILL.name)
            launchWhenStarted(galleryFlow, ::handleUiState)
        }
    }

    private fun handleUiState(galleryUiState: GalleryUiState) {
        with(binding) {
            when (galleryUiState) {
                is GalleryUiState.Loading -> flProgress.isVisible = true
                is GalleryUiState.Success -> {
                    flProgress.isVisible = false
                    adapter.submitList(galleryUiState.photos)
                }
                is GalleryUiState.Error -> {
                    flProgress.isVisible = false
                    tvError.isVisible = true
                    horizontalScrollView.isVisible = false
                    rvPhotos.isVisible = false
                }
            }
        }
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
