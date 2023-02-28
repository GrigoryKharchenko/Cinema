package kinopoisk.cinema.presentation.screen.detailphoto

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kinopoisk.cinema.databinding.FragmentDetailPhotoBinding
import kinopoisk.cinema.extension.loadImage
import kinopoisk.cinema.presentation.screen.filmdetail.FilmDetailFragment
import kinopoisk.cinema.presentation.screen.films.FilmsFragment
import javax.inject.Inject

class DetailPhotoFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private var _binding: FragmentDetailPhotoBinding? = null
    private val binding get() = _binding!!

    private val argument: String by lazy {
        arguments?.getString(FilmDetailFragment.KEY_PHOTO)
            ?: throw RuntimeException("${FilmsFragment::class.java.simpleName} must have argument")
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
        _binding = FragmentDetailPhotoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        with(binding) {
            toolBar.setNavigationOnClickListener { goBack() }
            ivPhoto.loadImage(argument)
        }
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
