package kinopoisk.cinema.presentation.screen.profilepage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kinopoisk.cinema.databinding.FragmentProfileBinding
import kinopoisk.cinema.di.ViewModelFactory
import kinopoisk.cinema.extension.launchWhenStarted
import kinopoisk.cinema.presentation.screen.profilepage.adapter.ProfileAdapter
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProfileFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var defaultViewModelFactory: ViewModelFactory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelFactory)[ProfileViewModel::class.java]
    }

    private val viewedAdapter = ProfileAdapter(onDelete = { viewModel.deleteAllViewedFilm() })
    private val interestingAdapter = ProfileAdapter(onDelete = { viewModel.deleteAllInterestingFilm() })

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
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        with(binding) {
            rvViewedFilm.adapter = viewedAdapter
            rvInterestingFilm.adapter = interestingAdapter
        }
    }

    private fun initViewModel() {
        launchWhenStarted(viewModel.profileUiState, ::handleUiState)
    }

    private fun handleUiState(profileUiState: ProfileUiState.Success) {
        with(binding) {
            tvCountViewedFilm.text = profileUiState.countViewedFilm
            viewedAdapter.submitList(profileUiState.filmViewed)
            tvCountInterestingFilm.text = profileUiState.countInterestingFilm
            interestingAdapter.submitList(profileUiState.filmInteresting)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
