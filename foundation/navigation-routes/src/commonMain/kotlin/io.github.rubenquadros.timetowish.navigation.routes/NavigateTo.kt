package io.github.rubenquadros.timetowish.navigation.routes

import androidx.navigation.NavOptionsBuilder

/**
 * Instead of passing multiple lambdas to a Composable for navigation to destinations, we now pass
 * a single [NavigateTo] which takes [TWDestination] and [NavOptionsBuilder] as params.
 */
typealias NavigateTo = (TWDestination, NavOptionsBuilder.() -> Unit) -> Unit

/**
 * Use this function when you do not have [NavOptionsBuilder] to be passed to [NavigateTo]
 */
fun NavigateTo.withDefaultOptions(destination: TWDestination) = this(destination) {
    //default no_op NavOptionsBuilder
}