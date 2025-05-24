package io.github.rubenquadros.timetowish.feature.home.domain.usecase

import io.github.rubenquadros.timetowish.core.base.BaseFlowUseCase
import io.github.rubenquadros.timetowish.core.base.UseCaseResult
import io.github.rubenquadros.timetowish.feature.home.domain.entity.Event
import io.github.rubenquadros.timetowish.feature.home.domain.repository.HomeRepository
import io.github.rubenquadros.timetowish.shared.domain.GetIsLoggedInUseCase
import io.github.rubenquadros.timetowish.shared.util.getToday
import org.koin.core.annotation.Factory

internal abstract class GetTodayEventsUseCase : BaseFlowUseCase<Unit, List<Event>>()

@Factory
internal class GetTodayEventsUseCaseImpl(
    private val homeRepository: HomeRepository,
    private val getIsLoggedInUseCase: GetIsLoggedInUseCase
) : GetTodayEventsUseCase() {
    override suspend fun execute(request: Unit): List<Event> {
        val loggedInResult = getIsLoggedInUseCase(Unit)
        val isLoggedIn = loggedInResult is UseCaseResult.Success && loggedInResult.data
        return if (isLoggedIn) {
            homeRepository.getTodayEvents(date = getToday())
        } else emptyList() //non logged in user
    }
}