package kinopoisk.cinema.presentation.screen.searchpage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.FragmentSearchBinding
import kinopoisk.cinema.di.ViewModelFactory
import kinopoisk.cinema.extension.addFragmentWithArgs
import kinopoisk.cinema.extension.launchWhenStarted
import kinopoisk.cinema.presentation.screen.filmdetail.FilmDetailFragment
import kinopoisk.cinema.presentation.screen.searchpage.adapter.SearchAdapter
import javax.inject.Inject

class SearchFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var defaultViewModelFactory: ViewModelFactory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelFactory)[SearchViewModel::class.java]
    }

    private val adapter by lazy {
        SearchAdapter(onOpenFilm = ::openDetailFilm)
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
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        with(binding) {
            etSearchFilm.doAfterTextChanged { text ->
                viewModel.setQuery(text?.toString() ?: "")
            }
            rvFilms.adapter = adapter
            adapter.addLoadStateListener { state ->
                rvFilms.isVisible = state.refresh != LoadState.Loading
                flProgress.isVisible = state.refresh == LoadState.Loading
            }
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            launchWhenStarted(films, adapter::submitData)
            launchWhenStarted(query, ::updateSearchQuery)
        }
    }

    private fun updateSearchQuery(searchQuery: String) {
        with(binding.etSearchFilm) {
            if ((text?.toString() ?: "") != searchQuery) {
                setText(searchQuery)
            }
        }
    }

    private fun openDetailFilm(filmId: Int) {
        addFragmentWithArgs<FilmDetailFragment>(
            containerId = R.id.fragmentContainer,
            args = bundleOf(FilmDetailFragment.KEY_FILM to filmId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvFilms.adapter = null
        _binding = null
    }
}
