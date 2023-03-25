package kinopoisk.cinema.presentation.screen.season

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.FragmentSeasonsBinding
import kinopoisk.cinema.extension.addChipGroup
import kinopoisk.cinema.extension.launchWhenStarted
import kinopoisk.cinema.presentation.screen.filmdetail.FilmDetailFragment
import kinopoisk.cinema.presentation.screen.season.adapter.SeasonAdapter
import javax.inject.Inject

class SeasonFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var defaultViewModelFactory: SeasonViewModel.Factory

    private val viewModel: SeasonViewModel by viewModels {
        SeasonViewModel.provideFactory(defaultViewModelFactory, serialInfo)
    }

    private val adapter by lazy {
        SeasonAdapter()
    }

    private val serialInfo: SerialInfo by lazy {
        requireNotNull(arguments?.getSerializable(FilmDetailFragment.KEY_SEASON) as? SerialInfo)
    }

    private var _binding: FragmentSeasonsBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentSeasonsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        subscribeViewModel()
    }

    private fun initUi() {
        with(binding) {
            rvEpisodes.adapter = adapter
            toolBar.title = serialInfo.nameFilm
            toolBar.setNavigationOnClickListener { goBack() }
        }
    }

    private fun subscribeViewModel() {
        launchWhenStarted(viewModel.uiState, ::handleUiState)
    }

    private fun handleUiState(seasonUiState: SeasonUiState) {
        with(binding) {
            when (seasonUiState) {
                SeasonUiState.Loading -> flProgress.isVisible = true
                is SeasonUiState.Success -> successUiState(seasonUiState)
                SeasonUiState.Error -> {
                    flProgress.isVisible = false
                    tvError.isVisible = true
                    groupSuccess.isVisible = false
                }
            }
        }
    }

    private fun successUiState(seasonUiState: SeasonUiState.Success) {
        with(binding) {
            flProgress.isVisible = false
            tvError.isVisible = false
            groupSuccess.isVisible = true
            if (seasonUiState.isFirstSeason) {
                addChipGroup(requireContext(),
                    chipGroup,
                    seasonUiState.countSeason,
                    onClickChip = { numberSeason ->
                        viewModel.getCurrentSeason(numberSeason)
                    })
            }
            tvNumberSeason.text = getString(
                R.string.season_number_season,
                seasonUiState.currentSeason
            )
            tvCountSeries.text = resources.getQuantityString(
                R.plurals.plurals_series,
                seasonUiState.countEpisodesInSeasons,
                seasonUiState.countEpisodesInSeasons
            )
            adapter.submitList(seasonUiState.episodes)
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
