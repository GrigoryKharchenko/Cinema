package kinopoisk.cinema.presentation.screen.films

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kinopoisk.cinema.R
import kinopoisk.cinema.data.TypeCategories
import kinopoisk.cinema.databinding.FragmentFilmsBinding
import kinopoisk.cinema.extension.addFragmentWithArgs
import kinopoisk.cinema.extension.launchWhenStarted
import kinopoisk.cinema.presentation.screen.filmdetail.FilmDetailFragment
import javax.inject.Inject

class FilmsFragment : Fragment(), HasAndroidInjector {

    private var _binding: FragmentFilmsBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        FilmsAdapter(onOpenFilm = ::openDetailFilm)
    }

    private val argument: TypeCategories by lazy {
        arguments?.getSerializable(KEY_FILMS) as? TypeCategories
            ?: throw RuntimeException("${FilmsFragment::class.java.simpleName} must have argument")
    }

    private val viewModel: FilmsViewModel by viewModels {
        FilmsViewModel.provideFactory(viewModelFactory, argument)
    }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: FilmsViewModel.Factory

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
        _binding = FragmentFilmsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initViewModel() {
        launchWhenStarted(viewModel.filmsFlow, adapter::submitData)
    }

    private fun initUi() {
        with(binding) {
            toolBar.setNavigationOnClickListener { goBack() }
            rvFilms.adapter = adapter
            adapter.addLoadStateListener { state ->
                flProgress.isVisible = state.refresh == LoadState.Loading
            }
            toolBar.title = argument.nameCategory
        }
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

    private fun openDetailFilm(filmId: Int) {
        addFragmentWithArgs<FilmDetailFragment>(
            containerId = R.id.fragmentContainer,
            args = bundleOf(FilmDetailFragment.KEY_FILM to filmId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_FILMS = "KeyFilms"
    }
}
