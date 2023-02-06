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
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.FragmentFilmDetailsBinding
import kinopoisk.cinema.di.ViewModelFactory
import kinopoisk.cinema.extension.addFragmentWithArgs
import kinopoisk.cinema.extension.launchWhenStarted
import kinopoisk.cinema.extension.loadCropImage
import kinopoisk.cinema.extension.loadImage
import kinopoisk.cinema.extension.toStringOrEmpty
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.gallery.GalleryAdapter
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.similar.SimilarFilmAdapter
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.staff.StaffAdapter
import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailModel
import javax.inject.Inject

class FilmDetailFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var defaultViewModelFactory: ViewModelFactory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private var _binding: FragmentFilmDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val actorAdapter by lazy { StaffAdapter() }
    private val staffAdapter by lazy { StaffAdapter() }
    private val galleryAdapter by lazy { GalleryAdapter() }
    private val similarFilmAdapter by lazy { SimilarFilmAdapter(onFilmClick = ::openSimilarFilm) }

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
            rvStaff.adapter = staffAdapter
            rvGallery.adapter = galleryAdapter
            rvSimilarFilm.adapter = similarFilmAdapter
            rvActors.adapter = actorAdapter
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            getFilmDetail(getArgs())
            launchWhenStarted(uiStateFlow, ::handleUiState)
        }
    }

    private fun handleUiState(filmDetailUiState: FilmDetailUiState) {
        when (filmDetailUiState) {
            FilmDetailUiState.Loading -> binding.flProgress.isVisible = true
            is FilmDetailUiState.DetailFilm -> updateUiState(filmDetailUiState)
        }
    }

    private fun updateUiState(filmDetailUiState: FilmDetailUiState.DetailFilm) {
        with(binding) {
            val gallery = filmDetailUiState.filmDetailUiModel.gallery
            val similar = filmDetailUiState.filmDetailUiModel.similar
            val staff = filmDetailUiState.filmDetailUiModel.staff
            val detailFilm = filmDetailUiState.filmDetailUiModel.detailFilm
            val actor = filmDetailUiState.filmDetailUiModel.actor
            flProgress.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleProgress
            tvError.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleTextError
            nestedScroll.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleNestedScroll
            appBar.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleAppBar
            tvTitleActor.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleActors
            tvCountActor.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleActors
            tvTitleStaff.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleStaff
            tvCountStaff.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleStaff
            tvTitleGallery.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleGallery
            tvCountGallery.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleGallery
            tvTitleSimilarFilm.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleSimilar
            tvCountSimilarFilm.isVisible = filmDetailUiState.filmDetailUiModel.isVisibleSimilar
            tvCountGallery.text = gallery?.size.toStringOrEmpty()
            tvCountSimilarFilm.text = similar?.size.toStringOrEmpty()
            tvCountStaff.text = staff?.size.toStringOrEmpty()
            tvCountActor.text = actor?.size.toStringOrEmpty()
            setDetailFilm(detailFilm)
            actorAdapter.submitList(actor)
            galleryAdapter.submitList(gallery)
            similarFilmAdapter.submitList(similar)
            staffAdapter.submitList(staff)
        }
    }

    private fun setDetailFilm(filmDetailModel: FilmDetailModel?) {
        filmDetailModel ?: return
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

    private fun openSimilarFilm(similarId: Int) {
        addFragmentWithArgs<FilmDetailFragment>(
            containerId = R.id.fragmentContainer,
            args = bundleOf(KEY_FILM to similarId)
        )
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        with(binding) {
            rvStaff.adapter = null
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
