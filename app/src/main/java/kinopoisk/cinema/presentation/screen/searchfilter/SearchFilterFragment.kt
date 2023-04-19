package kinopoisk.cinema.presentation.screen.searchfilter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kinopoisk.cinema.databinding.FragmentSearchFilterBinding
import kinopoisk.cinema.extension.launchWhenStarted
import kinopoisk.cinema.presentation.screen.searchfilter.adapter.SearchFilterAdapter
import javax.inject.Inject

class SearchFilterFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var defaultViewModelFactory: SearchFilterViewModel.Factory

    private var _binding: FragmentSearchFilterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchFilterViewModel by viewModels {
        SearchFilterViewModel.provideFactory(defaultViewModelFactory, typeFilterScreen)
    }

    private val typeFilterScreen: TypeFilterScreen by lazy {
        requireNotNull(arguments?.getSerializable(SEARCH_FILTER) as? TypeFilterScreen)
    }

    private val adapter by lazy { SearchFilterAdapter() }

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
        _binding = FragmentSearchFilterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        subscribeViewModel()
    }

    private fun initUi() {
        with(binding) {
            rvCountyAndGenre.adapter = adapter
            toolBar.setNavigationOnClickListener { goBack() }
        }
    }

    private fun subscribeViewModel() {
        with(viewModel) {
            launchWhenStarted(searchFilterUiState, ::handleUiState)
        }
    }

    private fun handleUiState(searchFilterUiState: SearchFilterUiState) {
        with(binding) {
            when (searchFilterUiState) {
                SearchFilterUiState.Loading -> {
                    flProgress.isVisible = true
                    groupSuccess.isVisible = false
                }
                is SearchFilterUiState.Success -> {
                    adapter.submitList(searchFilterUiState.countryOrGenre)
                    flProgress.isVisible = false
                    toolBar.title = getString(typeFilterScreen.typeTitle)
                    etSearch.hint = getString(typeFilterScreen.hintSearchField)
                    etSearch.doAfterTextChanged {
                        viewModel.getQuery(it.toString())
                    }
                }
                SearchFilterUiState.Error -> {
                    flProgress.isVisible = false
                    groupSuccess.isVisible = false
                    tvError.isVisible = true
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

    companion object {
        const val SEARCH_FILTER = "searchFilter"
    }
}
