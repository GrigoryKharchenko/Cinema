package kinopoisk.cinema.presentation.screen.homepage

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
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.FragmentHomeBinding
import kinopoisk.cinema.di.ViewModelFactory
import kinopoisk.cinema.extension.launchWhenStarted
import kinopoisk.cinema.presentation.screen.filmdetail.FilmDetailFragment
import kinopoisk.cinema.presentation.screen.homepage.allcategory.AllCategoryAdapter
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class HomeFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var defaultViewModelFactory: ViewModelFactory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelFactory)[HomeViewModel::class.java]
    }

    private val adapter by lazy {
        AllCategoryAdapter(
            onFilmClick = ::openFilmDetail
        )
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
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        with(binding) {
            rvCertainCategory.adapter = adapter
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            uiStateFlow.onEach(::handleUiState)
                .launchWhenStarted(lifecycleScope, viewLifecycleOwner.lifecycle)
        }
    }

    private fun handleUiState(homeUiState: HomeUiState) {
        with(binding) {
            when (homeUiState) {
                is HomeUiState.Error -> {
                    ivError.isVisible = true
                    tvError.isVisible = true
                    ivTitle.isVisible = false
                    rvCertainCategory.isVisible = false
                    flProgress.isVisible = false
                }
                is HomeUiState.Loading -> flProgress.isVisible = true
                is HomeUiState.Success -> {
                    flProgress.isVisible = false
                    adapter.submitList(homeUiState.films)
                }
            }
        }
    }

    private fun openFilmDetail(filmId: Int) {
        parentFragmentManager.beginTransaction()
            .addToBackStack(FilmDetailFragment::class.java.simpleName)
            .replace(R.id.fragmentContainer, FilmDetailFragment.newInstance(filmId))
            .commit()
    }

    override fun onDestroyView() {
        binding.rvCertainCategory.adapter = null
        super.onDestroyView()
        _binding = null
    }
}
