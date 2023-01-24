package kinopoisk.cinema.presentation.screen.filmdetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kinopoisk.cinema.databinding.FragmentFilmDetailsBinding
import kinopoisk.cinema.di.ViewModelFactory
import kinopoisk.cinema.extension.launchWhenStarted
import kinopoisk.cinema.extension.loadCropImage
import kinopoisk.cinema.extension.loadImage
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.gallery.GalleryAdapter
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.similar.SimilarFilmAdapter
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.stuff.StuffAdapter
import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FilmDetailFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var defaultViewModelFactory: ViewModelFactory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private var _binding: FragmentFilmDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val actorAdapter by lazy { StuffAdapter() }
    private val stuffAdapter by lazy { StuffAdapter() }
    private val galleryAdapter by lazy { GalleryAdapter() }
    private val similarFilmAdapter by lazy { SimilarFilmAdapter() }

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelFactory)[FilmDetailViewModel::class.java]
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
        _binding = FragmentFilmDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun getArgs(): Int = arguments?.getInt(KEY_FILM) ?: 0

    private fun initUi() {
        with(binding) {
            toolbar.setNavigationOnClickListener { goBack() }
            rvStuff.adapter = stuffAdapter
            rvGallery.adapter = galleryAdapter
            rvSimilarFilm.adapter = similarFilmAdapter
            rvActors.adapter = actorAdapter
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            getFilmDetail(getArgs())
            uiStateFlow.onEach(::handleUiState)
                .launchWhenStarted(lifecycleScope, viewLifecycleOwner.lifecycle)
        }
    }

    private fun handleUiState(filmDetailUiState: FilmDetailUiState) {
        when (filmDetailUiState) {
            FilmDetailUiState.Loading -> binding.flProgress.isVisible = true
            is FilmDetailUiState.Success -> successResponse(filmDetailUiState)
            is FilmDetailUiState.Error -> failureResponse(filmDetailUiState)
        }
    }

    private fun successResponse(filmDetailUiState: FilmDetailUiState.Success) {
        with(binding) {
            val gallery = filmDetailUiState.filmDetailUiModel.gallery
            val similar = filmDetailUiState.filmDetailUiModel.similar
            val stuff = filmDetailUiState.filmDetailUiModel.stuff
            val detailFilm = filmDetailUiState.filmDetailUiModel.detailFilm
            val actor = filmDetailUiState.filmDetailUiModel.actor
            flProgress.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleProgress
            tvError.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleTextError
            tvTitleActor.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleTitleActor
            tvCountActor.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleCountActor
            tvTitleStuff.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleTitleStuff
            tvCountStuff.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleCountStuff
            tvTitleGallery.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleTitleGallery
            tvCountGallery.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleCountGallery
            tvTitleSimilarFilm.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleTitleSimilar
            tvCountSimilarFilm.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleCountSimilar
            tvCountGallery.text = gallery.size.toString()
            tvCountSimilarFilm.text = similar.size.toString()
            tvCountStuff.text = stuff.size.toString()
            tvCountActor.text = actor.size.toString()
            setDetailFilm(detailFilm)
            actorAdapter.submitList(actor)
            galleryAdapter.submitList(gallery)
            similarFilmAdapter.submitList(similar)
            stuffAdapter.submitList(stuff)
        }
    }

    private fun failureResponse(filmDetailUiState: FilmDetailUiState.Error) {
        with(binding) {
            flProgress.isVisible = filmDetailUiState.errors.isVisibleProgress
            tvError.isVisible = filmDetailUiState.errors.isVisibleTextError
            nestedScroll.isVisible = filmDetailUiState.errors.isVisibleNestedScroll
            appBar.isVisible = filmDetailUiState.errors.isVisibleAppBar
            tvCountActor.isVisible = filmDetailUiState.errors.isVisibleCountActor
            tvTitleActor.isVisible = filmDetailUiState.errors.isVisibleTitleActor
            tvTitleStuff.isVisible = filmDetailUiState.errors.isVisibleTitleStuff
            tvCountStuff.isVisible = filmDetailUiState.errors.isVisibleCountStuff
            tvTitleGallery.isVisible = filmDetailUiState.errors.isVisibleTitleGallery
            tvCountGallery.isVisible = filmDetailUiState.errors.isVisibleCountGallery
            tvTitleSimilarFilm.isVisible = filmDetailUiState.errors.isVisibleTitleSimilar
            tvCountSimilarFilm.isVisible = filmDetailUiState.errors.isVisibleCountSimilar
        }
    }

    private fun setDetailFilm(filmDetailModel: FilmDetailModel) {
        with(filmDetailModel) {
            binding.ivPosterFilm.loadCropImage(poster)
            binding.ivLogo.loadImage(logo)
            binding.tvNameFilm.text = detailFilm
            binding.tvShortDescription.text = shortDescription
            binding.tvShortDescription.isVisible = isVisibleShortDescription
            binding.tvDescription.text = description
            binding.collapsingToolBar.title = name
            var isCollapsed = INITIAL_IS_COLLAPSED
            binding.tvDescription.setOnClickListener {
                if (isCollapsed) {
                    binding.tvDescription.maxLines = Int.MAX_VALUE
                } else {
                    binding.tvDescription.maxLines = MAX_LINE_COLLAPSED
                }
                isCollapsed = !isCollapsed
                binding.tvDescription.text = description
            }
        }
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        with(binding) {
            rvStuff.adapter = null
            rvGallery.adapter = null
            rvSimilarFilm.adapter = null
            rvActors.adapter = null
        }
        super.onDestroyView()
        _binding = null

    }

    companion object {
        const val KEY_FILM = "filmId"
        private const val MAX_LINE_COLLAPSED = 5
        private const val INITIAL_IS_COLLAPSED = true
    }
}
