package io.github.rubenquadros.timetowish.feature.home.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.rubenquadros.timetowish.core.base.SavedStateContainer
import io.github.rubenquadros.timetowish.feature.home.domain.entity.HomeEntity
import io.github.rubenquadros.timetowish.feature.home.domain.usecase.GetTodayEventAndProfileUseCase
import io.github.rubenquadros.timetowish.feature.home.presentation.ui.grid.HomeGridItemType
import io.github.rubenquadros.timetowish.navigation.routes.generatewish.GenerateWishScreen
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
) : ViewModel() {

    private val homeContainer = SavedStateContainer<HomeUiState, HomeEvent>(
        scope = viewModelScope,
        savedStateHandle = savedStateHandle,
        kSerializer = HomeUiState.serializer(),
        initialState = HomeUiState.Loading,
        loadInitialData = ::loadInitialData,

    )

    val uiState by homeContainer::uiState
    val uiEvent by homeContainer::uiEvent

    fun onCardClick(itemType: HomeGridItemType) {
        when (itemType) {
            HomeGridItemType.CALENDAR, HomeGridItemType.ADD_EVENT -> {
                viewModelScope.launch {
                    loginDelegate.checkLoginState(
                        onLoggedIn = {
                            //updateEvent()
                        },
                        onNotLoggedIn = {
                            homeContainer.updateEvent(HomeEvent.Navigate(LoginScreen))
                        }
                    )
                }
            }

            HomeGridItemType.GREETING_CARD, HomeGridItemType.CREATE_WISH -> {
                homeContainer.updateEvent(HomeEvent.Navigate(GenerateWishScreen))
            }
        }
    }

    fun reloadInitialData() = homeContainer.reloadInitialData()

    private fun loadInitialData(): Flow<HomeUiState> {
        val homeEntityFlow: Flow<HomeEntity>? = getTodayEventAndProfileUseCase(Unit)

        return homeEntityFlow?.map { homeEntity ->
            if (homeEntity.todayEvents == null && homeEntity.currentUser == null) {
                HomeUiState.Error
            } else {
                HomeUiState.Data(homeEntity)
            }
        } ?: flow { emit(HomeUiState.Error) }
    }
}