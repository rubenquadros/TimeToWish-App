package io.github.rubenquadros.timetowish.feature.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.rubenquadros.timetowish.core.base.StateContainer
import io.github.rubenquadros.timetowish.navigation.routes.TWDestination
import io.github.rubenquadros.timetowish.navigation.routes.generatewish.GenerateWishScreen
import io.github.rubenquadros.timetowish.navigation.routes.home.HomeScreen
import io.github.rubenquadros.timetowish.shared.delegate.LoginDelegate
import kotlinx.coroutines.flow.Flow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LandingViewModel(
    private val loginDelegate: LoginDelegate
) : ViewModel() {

    private val landingContainer = StateContainer<Unit, LandingScreenNavEvent>(
        scope = viewModelScope,
        initialState = Unit
    )

    val uiEvent: Flow<LandingScreenNavEvent> by landingContainer::uiEvent

    fun onClick(destination: TWDestination) {
        val event: LandingScreenNavEvent = when (destination) {
            HomeScreen -> LandingScreenNavEvent.TabNavigation(destination)
            GenerateWishScreen -> LandingScreenNavEvent.FeatureNavigation(destination)
            else -> error("Unknown destination: $destination")
        }

        landingContainer.updateEvent(event)
    }
}