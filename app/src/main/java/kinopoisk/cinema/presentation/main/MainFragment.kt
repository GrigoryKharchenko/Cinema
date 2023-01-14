package kinopoisk.cinema.presentation.main

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
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.FragmentMainBinding
import kinopoisk.cinema.extension.replaceFragment
import kinopoisk.cinema.presentation.screen.homepage.HomeFragment
import kinopoisk.cinema.presentation.screen.profilepage.ProfileFragment
import kinopoisk.cinema.presentation.screen.searchpage.SearchFragment
import javax.inject.Inject

class MainFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        replaceFragment<HomeFragment>(R.id.fragmentContainer)
    }

    private fun initUi() {
        with(binding) {
            bottomNavigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.homePage -> replaceFragment<HomeFragment>(R.id.fragmentContainer)
                    R.id.searchPage -> replaceFragment<SearchFragment>(R.id.fragmentContainer)
                    R.id.profilePage -> replaceFragment<ProfileFragment>(R.id.fragmentContainer)
                    else -> throw IllegalStateException("Bottom Navigation has’t got this fragment")
                }
                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
