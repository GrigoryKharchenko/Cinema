package kinopoisk.cinema.presentation.screen.filmdetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
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
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.filmcrew.ActorAdapter
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.filmcrew.FilmCrewAdapter
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.gallery.GalleryAdapter
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.similar.SimilarAdapter
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FilmDetailFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var defaultViewModelFactory: ViewModelFactory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private var _binding: FragmentFilmDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val actorAdapter by lazy { ActorAdapter() }
    private val filmCrewAdapter by lazy { FilmCrewAdapter() }
    private val galleryAdapter by lazy { GalleryAdapter() }
    private val similarAdapter by lazy { SimilarAdapter() }

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

    private fun getArgs(): Int? = arguments?.getInt(KEY_FILM)

    private fun initUi() {
        with(binding) {
            toolbar.setNavigationOnClickListener { goBack() }
            rvFilmCrew.adapter = filmCrewAdapter
            rvGallery.adapter = galleryAdapter
            rvSimilarFilm.adapter = similarAdapter
            rvActors.adapter = actorAdapter
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            getFilmDetail(getArgs() ?: 0)
            uiStateFlow.onEach {
                handleUiState(it)
            }
                .launchWhenStarted(lifecycleScope, viewLifecycleOwner.lifecycle)
        }
    }

    private fun handleUiState(filmDetailUiState: FilmDetailUiState) {
        when (filmDetailUiState) {
            is FilmDetailUiState.Loading -> binding.flProgress.isVisible = true
            is FilmDetailUiState.Success -> {
                with(binding) {
                    binding.flProgress.isVisible = false
                    binding.tvError.isVisible = false
                    val gallery = filmDetailUiState.gallery
                    val similar = filmDetailUiState.similar
                    val filmCrew = filmDetailUiState.filmCrew
                    val detailFilm = filmDetailUiState.detailFilm
                    val actor = filmDetailUiState.actor
                    tvCountGallery.text = gallery.size.toString()
                    tvCountSimilarFilm.text = similar.size.toString()
                    tvCountFilmWorked.text = filmCrew.size.toString()
                    tvCountActor.text = actor.size.toString()
                    setDetailFilm(detailFilm)
                    actorAdapter.submitList(actor)
                    galleryAdapter.submitList(gallery)
                    similarAdapter.submitList(similar)
                    filmCrewAdapter.submitList(filmCrew)
                    if (gallery.isEmpty()) {
                        tvGallery.isVisible = false
                        tvCountGallery.isVisible = false
                    }
                    if (similar.isEmpty()) {
                        tvSimilarFilm.isVisible = false
                        tvCountSimilarFilm.isVisible = false
                    }
                }
            }
            is FilmDetailUiState.Error -> {
                binding.flProgress.isVisible = false
                binding.tvError.isVisible = true
                binding.appBar.isVisible = false
                binding.nestedScroll.isVisible = false
            }
        }
    }

    private fun setDetailFilm(filmDetailUiModel: FilmDetailUiModel) {
        with(filmDetailUiModel) {
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
        binding.rvFilmCrew.adapter = null
        binding.rvGallery.adapter = null
        binding.rvSimilarFilm.adapter = null
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KEY_FILM = "filmId"
        private const val MAX_LINE_COLLAPSED = 5
        private const val INITIAL_IS_COLLAPSED = true
        fun newInstance(id: Int) = FilmDetailFragment().apply {
            arguments = bundleOf(KEY_FILM to id)
        }
    }
}
