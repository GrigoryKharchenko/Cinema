package kinopoisk.cinema.data.repository

import kinopoisk.cinema.domain.repository.PeriodRepository
import kinopoisk.cinema.presentation.screen.period.PeriodModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class PeriodRepositoryImpl @Inject constructor() : PeriodRepository {

    private val _yearFlow = MutableSharedFlow<PeriodModel>(replay = 1)
    override val yearFlow: SharedFlow<PeriodModel> = _yearFlow.asSharedFlow()

    override suspend fun addYear(periodModel: PeriodModel) {
        _yearFlow.emit(PeriodModel(periodModel.startPeriod, periodModel.endPeriod))
    }
}
