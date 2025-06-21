package io.github.rubenquadros.timetowish.shared.presentation.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

val exitFadeOutTransition = fadeOut(tween(300))
val enterFadeInTransition = fadeIn(tween(500), initialAlpha = 0.3f)