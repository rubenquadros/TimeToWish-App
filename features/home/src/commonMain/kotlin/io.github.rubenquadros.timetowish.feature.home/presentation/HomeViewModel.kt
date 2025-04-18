package io.github.rubenquadros.timetowish.feature.home.presentation

import androidx.lifecycle.SavedStateHandle
import io.github.rubenquadros.timetowish.core.base.viewmodel.BaseViewModel
import io.github.rubenquadros.timetowish.feature.home.domain.entity.HomeEntity
import io.github.rubenquadros.timetowish.feature.home.domain.usecase.GetTodayEventAndProfileUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class HomeViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTodayEventAndProfileUseCase: GetTodayEventAndProfileUseCase
) : BaseViewModel<HomeUiState>(
    savedStateHandle = savedStateHandle,
    initialState = HomeUiState.Loading
) {

    override fun loadInitialData(): Flow<HomeUiState> {
        val homeEntityFlow: Flow<HomeEntity>? = getTodayEventAndProfileUseCase(Unit)

        return homeEntityFlow?.map { homeEntity ->
            if (homeEntity.todayEvents == null && homeEntity.currentUser == null) {
                HomeUiState.Error
            } else {
                HomeUiState.Data(homeEntity)
            }
        } ?: flow { emit(HomeUiState.Error) }
    }

    fun retryOnError() = reloadInitialData()
}