package io.github.rubenquadros.timetowish.feature.login.presentation

sealed interface LoginEvent {
    data object LoginSuccess : LoginEvent
}