package kinopoisk.cinema.presentation.screen.searchsettings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.FragmentSearchSettingsBinding
import kinopoisk.cinema.di.ViewModelFactory
import kinopoisk.cinema.extension.addFragment
import kinopoisk.cinema.extension.addFragmentWithArgs
import kinopoisk.cinema.extension.launchWhenStarted
import kinopoisk.cinema.presentation.screen.period.PeriodFragment
import kinopoisk.cinema.presentation.screen.searchfilter.SearchFilterFragment
import kinopoisk.cinema.presentation.screen.searchfilter.TypeFilterScreen
import javax.inject.Inject

class SearchSettingsFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var defaultViewModelFactory: ViewModelFactory

    private var _binding: FragmentSearchSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelFactory)[SearchSettingsViewModel::class.java]
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        with(binding) {
            toolBar.setNavigationOnClickListener { goBack() }
            tvChosenCountry.setOnClickListener { openSearchFilter(TypeFilterScreen.createCountry()) }
            tvChosenGenre.setOnClickListener { openSearchFilter(TypeFilterScreen.createGenre()) }
            tvChosenYear.setOnClickListener { openPeriod() }
            getPeriodYear()
        }
    }

    private fun openSearchFilter(typeFilterScreen: TypeFilterScreen) {
        addFragmentWithArgs<SearchFilterFragment>(
            containerId = R.id.fragmentContainer,
            args = bundleOf(SearchFilterFragment.SEARCH_FILTER to typeFilterScreen)
        )
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
        initUi()
    }

    private fun openPeriod() {
        addFragment<PeriodFragment>(
            containerId = R.id.fragmentContainer
        )
    }

    private fun getPeriodYear() {
        launchWhenStarted(viewModel.yearFlow, ::handleUiState)
    }

    private fun handleUiState(uiState: SearchSettingsUiState) {
        when (uiState) {
            is SearchSettingsUiState.Init -> binding.tvChosenYear.text = getString(uiState.period)
            is SearchSettingsUiState.Initialized -> binding.tvChosenYear.text =
                getString(R.string.search_setting_year_period, uiState.period.startPeriod, uiState.period.endPeriod)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
