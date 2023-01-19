package kinopoisk.cinema.presentation.screen.welcome

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.FragmentWelcomeBinding
import kinopoisk.cinema.extension.replaceFragment
import kinopoisk.cinema.presentation.main.MainFragment
import javax.inject.Inject

class WelcomeFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    // TODO: оба приватные, зачем два?
    private var _binding: FragmentWelcomeBinding? = null
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
        _binding = FragmentWelcomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    // TODO: такая функция планируется в каждом фрагменте? может вынести в общий асбтрактный класс? заодно и  androidInjector туда вытащить и onAttach? возможно и биндинг туда как-то затащить можно, чтобы не копировать целиком всё как сейчас сделано во всех фрагментах?....
    private fun initUi() {
        with(binding) {
            viewPager.adapter = WelcomeViewPager()
            TabLayoutMediator(tabLayout, viewPager) { _, _ ->
            }.attach()
            tvSkip.setOnClickListener {
                replaceFragment<MainFragment>(R.id.container)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
