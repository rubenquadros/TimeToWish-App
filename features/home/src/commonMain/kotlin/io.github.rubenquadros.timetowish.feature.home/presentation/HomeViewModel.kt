package io.github.rubenquadros.timetowish.feature.home.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import io.github.rubenquadros.timetowish.core.base.viewmodel.BaseViewModel
import io.github.rubenquadros.timetowish.feature.home.domain.entity.HomeEntity
import io.github.rubenquadros.timetowish.feature.home.domain.usecase.GetTodayEventAndProfileUseCase
import io.github.rubenquadros.timetowish.feature.home.presentation.ui.grid.HomeGridItemType
import io.github.rubenquadros.timetowish.navigation.routes.login.LoginRoute.LoginScreen
import io.github.rubenquadros.timetowish.shared.delegate.LoginDelegate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class HomeViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTodayEventAndProfileUseCase: GetTodayEventAndProfileUseCase,
    private val loginDelegate: LoginDelegate
) : BaseViewModel<HomeUiState, HomeEvent>(
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

    fun onCardClick(itemType: HomeGridItemType) {
        when (itemType) {
            HomeGridItemType.CALENDAR, HomeGridItemType.ADD_EVENT -> {
                viewModelScope.launch {
                    loginDelegate.checkLoginState(
                        onLoggedIn = {
                            //updateEvent()
                        },
                        onNotLoggedIn = {
                            updateEvent(HomeEvent.Navigate(LoginScreen))
                        }
                    )
                }
            }

            HomeGridItemType.GREETING_CARD, HomeGridItemType.CREATE_WISH -> {
                //updateEvent()
            }
        }
    }
}