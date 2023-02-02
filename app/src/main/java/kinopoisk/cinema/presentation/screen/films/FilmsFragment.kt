package kinopoisk.cinema.presentation.screen.films

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kinopoisk.cinema.data.TypeCategories
import kinopoisk.cinema.databinding.FragmentFilmsBinding
import kinopoisk.cinema.extension.launchWhenStarted
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FilmsFragment : Fragment(), HasAndroidInjector {

    private var _binding: FragmentFilmsBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { FilmsAdapter() }

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
        with(viewModel) {
            filmsFlow.onEach(adapter::submitData)
                .launchWhenStarted(lifecycleScope, viewLifecycleOwner.lifecycle)
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_FILMS = "KeyFilms"
    }
}
