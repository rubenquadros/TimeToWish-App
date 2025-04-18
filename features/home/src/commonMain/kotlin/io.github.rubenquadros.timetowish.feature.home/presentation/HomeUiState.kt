package io.github.rubenquadros.timetowish.feature.home.presentation

import io.github.rubenquadros.timetowish.feature.home.domain.entity.HomeEntity

internal sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Data(val homeEntity: HomeEntity) : HomeUiState

    data object Error : HomeUiState
}