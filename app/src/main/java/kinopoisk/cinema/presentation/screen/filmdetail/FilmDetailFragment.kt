package kinopoisk.cinema.presentation.screen.filmdetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.FragmentFilmDetailsBinding
import kinopoisk.cinema.extension.addFragmentWithArgs
import kinopoisk.cinema.extension.launchWhenStarted
import kinopoisk.cinema.extension.loadCropImage
import kinopoisk.cinema.extension.loadImage
import kinopoisk.cinema.presentation.screen.actor.ActorFragment
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.gallery.GalleryAdapter
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.similar.SimilarFilmAdapter
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.staff.StaffAdapter
import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailUiModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.GalleryModel
import kinopoisk.cinema.presentation.screen.fullscreenphoto.FullScreenPhotoFragment
import kinopoisk.cinema.presentation.screen.season.SerialInfo
import kinopoisk.cinema.presentation.screen.season.SeasonFragment
import kinopoisk.cinema.presentation.screen.staff.StaffFragment
import kinopoisk.cinema.presentation.screen.staff.TypeTitleStaff
import javax.inject.Inject

class FilmDetailFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var viewModelFactory: FilmDetailViewModel.Factory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private var _binding: FragmentFilmDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val filmId: Int by lazy {
        requireNotNull(arguments?.getInt(KEY_FILM))
    }

    private val actorAdapter by lazy { StaffAdapter(onStaffClick = ::openDetailStaff) }
    private val staffAdapter by lazy { StaffAdapter(onStaffClick = ::openDetailStaff) }
    private val galleryAdapter by lazy { GalleryAdapter(onPhotoClick = ::openDetailPhoto) }
    private val similarFilmAdapter by lazy { SimilarFilmAdapter(onFilmClick = ::openSimilarFilm) }

    private val viewModel: FilmDetailViewModel by viewModels {
        FilmDetailViewModel.provideFactory(viewModelFactory, filmId)
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

    private fun initUi() {
        with(binding) {
            toolbar.setNavigationOnClickListener { goBack() }
            rvStaff.adapter = staffAdapter
            rvGallery.adapter = galleryAdapter
            rvSimilarFilm.adapter = similarFilmAdapter
            rvActors.adapter = actorAdapter
            tvCountActor.setOnClickListener {
                openStaff(
                    typeTitleStaff = TypeTitleStaff.createActor(filmId)
                )
            }
            tvCountStaff.setOnClickListener {
                openStaff(
                    typeTitleStaff = TypeTitleStaff.createStaff(filmId)
                )
            }
        }
    }

    private fun initViewModel() {
        with(viewModel) {
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
        with(filmDetailUiState.filmDetailUiModel) {
            with(binding) {
                val gallery = gallery
                val similar = similar
                val staff = staff
                val detailFilm = detailFilm
                val actor = actor
                val countSeason = countSeason
                val countEpisodes = countEpisodes
                flProgress.isVisible = isVisibleProgress
                tvError.isVisible = isVisibleTextError
                nestedScroll.isVisible = isVisibleNestedScroll
                appBar.isVisible = isVisibleAppBar
                groupActor.isVisible = isVisibleActors
                groupStaff.isVisible = isVisibleStaff
                groupGallery.isVisible = isVisibleGallery
                groupSimilar.isVisible = isVisibleSimilar
                groupSerials.isVisible = isSerial
                tvCountGallery.text = sizeGallery
                tvCountSimilarFilm.text = sizeSimilar
                tvCountStaff.text = sizeStuff
                tvCountActor.text = sizeActor
                setDetailFilm(detailFilm)
                actorAdapter.submitList(actor)
                galleryAdapter.submitList(gallery)
                similarFilmAdapter.submitList(similar)
                staffAdapter.submitList(staff)
                tvCountSeasons.text = resources.getQuantityString(R.plurals.plurals_season, countSeason, countSeason)
                tvCountSeries.text = resources.getQuantityString(R.plurals.plurals_series, countEpisodes, countEpisodes)
            }
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
            binding.tvAllSeason.setOnClickListener {
                openSeason(SerialInfo(name, filmId))
            }
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

    private fun openDetailStaff(stuffId: Int) {
        addFragmentWithArgs<ActorFragment>(
            containerId = R.id.fragmentContainer,
            args = bundleOf(ActorFragment.KEY_STAFF to stuffId)
        )
    }

    private fun openDetailPhoto(galleryModel: GalleryModel) {
        addFragmentWithArgs<FullScreenPhotoFragment>(
            containerId = R.id.fragmentContainer,
            args = bundleOf(KEY_PHOTO to galleryModel.image)
        )
    }

    private fun openStaff(typeTitleStaff: TypeTitleStaff) {
        addFragmentWithArgs<StaffFragment>(
            containerId = R.id.fragmentContainer,
            args = bundleOf(KEY_FILM to typeTitleStaff)
        )
    }

    private fun openSeason(serialInfo: SerialInfo) {
        addFragmentWithArgs<SeasonFragment>(
            containerId = R.id.fragmentContainer,
            args = bundleOf(KEY_SEASON to serialInfo)
        )
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        with(binding) {
            rvStaff.adapter = null
            rvGallery.adapter = null
            rvSimilarFilm.adapter = null
            rvActors.adapter = null
        }
        _binding = null
    }

    companion object {
        const val KEY_FILM = "filmId"
        const val KEY_PHOTO = "photoId"
        const val KEY_SEASON = "seasonId"
        private const val MAX_LINE_COLLAPSED = 5
        private const val INITIAL_IS_COLLAPSED = true
    }
}
