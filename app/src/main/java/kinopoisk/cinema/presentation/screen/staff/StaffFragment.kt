package kinopoisk.cinema.presentation.screen.staff

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.FragmentStaffBinding
import kinopoisk.cinema.extension.addFragmentWithArgs
import kinopoisk.cinema.extension.launchWhenStarted
import kinopoisk.cinema.presentation.screen.actor.ActorFragment
import kinopoisk.cinema.presentation.screen.filmdetail.FilmDetailFragment
import kinopoisk.cinema.presentation.screen.staff.adapter.StaffAdapter
import javax.inject.Inject

class StaffFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var defaultViewModelFactory: StaffViewModel.Factory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val typeTitleStaff: TypeTitleStaff by lazy {
        requireNotNull(arguments?.getSerializable(FilmDetailFragment.KEY_FILM) as? TypeTitleStaff)
    }

    private val viewModel: StaffViewModel by viewModels {
        StaffViewModel.provideFactory(defaultViewModelFactory, typeTitleStaff)
    }

    private var _binding: FragmentStaffBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        StaffAdapter(onStaffClick = ::openDetailStaff)
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
        _binding = FragmentStaffBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        subscribeViewModel()
    }

    private fun initUi() {
        with(binding) {
            rvStaff.adapter = adapter
            toolBar.setNavigationOnClickListener { goBack() }
            tvTitleStaff.setText(typeTitleStaff.titleStaff)
            btnRetry.setOnClickListener { viewModel.getStaff() }
        }
    }

    private fun subscribeViewModel() {
        with(viewModel) {
            launchWhenStarted(staffUiStateFlow, ::handleUiState)
        }
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

    private fun handleUiState(staffUiState: StaffUiState) {
        with(binding) {
            when (staffUiState) {
                StaffUiState.Loading -> flProgress.isVisible = true
                StaffUiState.Error -> {
                    flProgress.isVisible = false
                    tvError.isVisible = true
                    btnRetry.isVisible = true
                    tvTitleStaff.isVisible = false
                }
                is StaffUiState.Success -> {
                    flProgress.isVisible = false
                    tvError.isVisible = false
                    btnRetry.isVisible = false
                    adapter.submitList(staffUiState.staff)
                }
            }
        }
    }

    private fun openDetailStaff(stuffId: Int) {
        addFragmentWithArgs<ActorFragment>(
            containerId = R.id.fragmentContainer,
            args = bundleOf(ActorFragment.KEY_STAFF to stuffId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
