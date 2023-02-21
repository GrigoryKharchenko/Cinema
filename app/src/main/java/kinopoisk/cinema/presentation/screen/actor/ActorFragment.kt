package kinopoisk.cinema.presentation.screen.actor

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.FragmentActorBinding
import kinopoisk.cinema.extension.launchWhenStarted
import kinopoisk.cinema.extension.loadCropImage
import kinopoisk.cinema.presentation.screen.actor.adapter.BestFilmsActorAdapter
import javax.inject.Inject

class ActorFragment : Fragment(), HasAndroidInjector {

    private var _binding: FragmentActorBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ActorViewModel.Factory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val argument: Int by lazy {
        arguments?.getInt(KEY_STAFF)
            ?: throw RuntimeException("${ActorFragment::class.java.simpleName} must have argument")
    }

    private val adapter = BestFilmsActorAdapter()


    private val viewModel: ActorViewModel by viewModels {
        ActorViewModel.provideFactory(viewModelFactory, argument)
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
        _binding = FragmentActorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        with(binding) {
            toolBar.setNavigationOnClickListener { goBackScreen() }
            rvSimilarFilm.adapter = adapter
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            launchWhenStarted(actorFlow, ::handleUiState)
        }
    }

    private fun handleUiState(actorUiState: ActorUiState) {
        when (actorUiState) {
            ActorUiState.Loading -> binding.flProgress.isVisible = true
            is ActorUiState.Success -> setActor(actorUiState)
            ActorUiState.Error -> setError()
        }
    }

    private fun setActor(state: ActorUiState.Success) {
        with(binding) {
            flProgress.isVisible = false
            ivPhotoActor.loadCropImage(state.actorModel.photo)
            tvNameActor.text = state.actorModel.name
            tvProfession.text = state.actorModel.profession
            val countFilms = state.actorModel.countFilm
            tvCountFilm.text = resources.getQuantityString(R.plurals.plurals_film, countFilms, countFilms)
            adapter.submitList(state.actorModel.bestFilms)
        }
    }

    private fun setError() {
        with(binding) {
            group.isGone = true
            tvError.isVisible = true
            flProgress.isGone = true
        }
    }

    private fun goBackScreen() {
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_STAFF = "staffId"
    }
}
