package kinopoisk.cinema

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kinopoisk.cinema.di.DaggerAppComponent

class CinemaApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent
            .builder()
            .context(this)
            .build()
}
