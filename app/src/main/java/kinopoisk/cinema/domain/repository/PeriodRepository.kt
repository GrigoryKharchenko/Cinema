package kinopoisk.cinema.domain.repository

import kinopoisk.cinema.presentation.screen.period.PeriodModel
import kotlinx.coroutines.flow.SharedFlow

interface PeriodRepository {

    val yearFlow: SharedFlow<PeriodModel>

    suspend fun addYear(periodModel: PeriodModel)
}
