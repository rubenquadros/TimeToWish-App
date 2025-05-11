package io.github.rubenquadros.timetowish.feature.home.domain.usecase

import io.github.rubenquadros.timetowish.core.base.usecase.UseCase
import io.github.rubenquadros.timetowish.core.base.usecase.UseCaseResult
import io.github.rubenquadros.timetowish.core.session.CurrentUser
import io.github.rubenquadros.timetowish.feature.home.domain.entity.Event
import io.github.rubenquadros.timetowish.feature.home.domain.entity.HomeEntity
import io.github.rubenquadros.timetowish.shared.domain.ObserveCurrentUserUseCase
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.zip
import org.koin.core.annotation.Factory

internal abstract class GetTodayEventAndProfileUseCase : UseCase<Unit, Flow<HomeEntity>>()

@Factory
internal class GetTodayEventAndProfileUseCaseImpl(
    private val getCurrentUserUseCase: ObserveCurrentUserUseCase,
    private val getTodayEventsUseCase: GetTodayEventsUseCase
) : GetTodayEventAndProfileUseCase() {
    override fun execute(request: Unit): Flow<HomeEntity> {
        val todayEventsFlow: Flow<UseCaseResult<List<Event>>> = getTodayEventsUseCase(Unit)

        val currentUserFlow: Flow<CurrentUser>? = getCurrentUserUseCase(Unit)

        val homeEntityFlow: Flow<HomeEntity> = todayEventsFlow.combine(currentUserFlow?: emptyFlow()) { todayEventsResult, currentUser ->
            val todayEvents = if (todayEventsResult is UseCaseResult.Success) todayEventsResult.data else null

            HomeEntity(
                currentUser = currentUser,
                todayEvents = todayEvents?.toImmutableList()
            )
        }

        return homeEntityFlow
    }
}