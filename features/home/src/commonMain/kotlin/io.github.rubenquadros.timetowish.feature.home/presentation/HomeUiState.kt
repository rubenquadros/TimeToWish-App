package io.github.rubenquadros.timetowish.feature.home.presentation

import io.github.rubenquadros.timetowish.feature.home.domain.entity.HomeEntity
import kotlinx.serialization.Serializable

@Serializable
internal sealed interface HomeUiState {
    @Serializable
    data object Loading : HomeUiState

    @Serializable
    data class Data(val homeEntity: HomeEntity) : HomeUiState

    @Serializable
    data object Error : HomeUiState
}