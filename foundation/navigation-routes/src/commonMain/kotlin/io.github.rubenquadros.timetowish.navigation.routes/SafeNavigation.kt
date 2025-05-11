package io.github.rubenquadros.timetowish.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun <T: Any>NavController.safeNavigate(route: T, navOptionsBuilder: NavOptionsBuilder.() -> Unit) {
    runCatching {
        this.navigate(route = route, builder = navOptionsBuilder)
    }.onFailure {
        //log this failure for the route
    }
}