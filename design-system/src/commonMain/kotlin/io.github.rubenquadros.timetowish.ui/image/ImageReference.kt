package io.github.rubenquadros.timetowish.ui.image

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.DrawableResource

sealed interface ImageReference {
    @Immutable
    data class ResImage(val res: DrawableResource) : ImageReference

    @Immutable
    data class ServerImage(val imageUrl: String, val fallback: DrawableResource? = null) : ImageReference

}