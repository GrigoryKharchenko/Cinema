package kinopoisk.cinema.presentation.screen.homepage

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
import kinopoisk.cinema.data.TypeCategories
import kinopoisk.cinema.databinding.FragmentHomeBinding
import kinopoisk.cinema.di.ViewModelFactory
import kinopoisk.cinema.extension.addFragmentWithArgs
import kinopoisk.cinema.extension.launchWhenStarted
import kinopoisk.cinema.presentation.screen.filmdetail.FilmDetailFragment
import kinopoisk.cinema.presentation.screen.films.FilmsFragment
import kinopoisk.cinema.presentation.screen.homepage.allcategory.AllCategoryAdapter
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
            onFilmClick = ::openFilmDetail,
            onShowAllClick = ::openFilmsFragment
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
        binding.rvCertainCategory.adapter = adapter
    }

    private fun initViewModel() {
        launchWhenStarted(viewModel.uiStateFlow, ::handleUiState)
    }

    private fun handleUiState(homeUiState: HomeUiState) {
        with(binding) {
            when (homeUiState) {
                is HomeUiState.Error -> {
                    tvError.isVisible = true
                    ivTitle.isVisible = false
                    rvCertainCategory.isVisible = false
                    flProgress.isVisible = false
                }
                is HomeUiState.Loading -> {
                    flProgress.isVisible = true
                    ivTitle.isVisible = false
                }
                is HomeUiState.Success -> {
                    flProgress.isVisible = false
                    ivTitle.isVisible = true
                    adapter.submitList(homeUiState.films)
                }
            }
        }
    }

    private fun openFilmsFragment(typeCategories: TypeCategories) {
        addFragmentWithArgs<FilmsFragment>(
            containerId = R.id.fragmentContainer,
            args = bundleOf(FilmsFragment.KEY_FILMS to typeCategories)
        )
    }

    private fun openFilmDetail(filmId: Int) {
        addFragmentWithArgs<FilmDetailFragment>(
            containerId = R.id.fragmentContainer,
            args = bundleOf(FilmDetailFragment.KEY_FILM to filmId)
        )
    }

    override fun onDestroyView() {
        binding.rvCertainCategory.adapter = null
        super.onDestroyView()
        _binding = null
    }
}
