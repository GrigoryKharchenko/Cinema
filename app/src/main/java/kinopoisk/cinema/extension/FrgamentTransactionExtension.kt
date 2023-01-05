package kinopoisk.cinema.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace

inline fun <reified F : Fragment> Fragment.replaceFragment(containerId: Int) {
    parentFragmentManager.commit {
        setReorderingAllowed(true)
        replace<F>(containerId)
    }
}

inline fun <reified F : Fragment> Fragment.addFragment(containerId: Int) {
    parentFragmentManager.commit {
        setReorderingAllowed(true)
        addToBackStack(F::class.java.simpleName)
        replace<F>(containerId)
    }
}
