package io.github.rubenquadros.timetowish.ui.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import coil3.compose.AsyncImage
import io.github.rubenquadros.timetowish.ui.TWTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import timetowish.design_system.generated.resources.Res
import timetowish.design_system.generated.resources.compose_multiplatform_logo

/**
 * @see [io.github.rubenquadros.timetowish.ui.preview.image.TWImagePreview]
 */
@Composable
fun TWImage(
    imageReference: ImageReference,
    accessibilityLabel: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    tint: Color? = null
) {

    if (LocalInspectionMode.current) {
        InspectionImageInternal(modifier, imageReference, accessibilityLabel, contentScale, tint)
        return
    }

    when (imageReference) {
        is ImageReference.ResImage -> {
            Image(
                modifier = modifier,
                painter = painterResource(getImageModel(imageReference) as DrawableResource),
                contentDescription = accessibilityLabel,
                contentScale = contentScale,
                colorFilter = tint?.let {
                    ColorFilter.tint(color = tint)
                },
            )
        }

        else -> {
            AsyncImage(
                modifier = modifier,
                model = getImageModel(imageReference),
                contentDescription = accessibilityLabel,
                contentScale = contentScale,
                colorFilter = tint?.let {
                    ColorFilter.tint(color = tint)
                },
                error = getFallback(imageReference)?.let {
                    painterResource(it)
                }
            )
        }
    }
}

private fun getImageModel(imageReference: ImageReference): Any {
    return when (imageReference) {
        is ImageReference.ResImage -> imageReference.res
        is ImageReference.ServerImage -> imageReference.imageUrl
    }
}

private fun getFallback(imageReference: ImageReference): DrawableResource? {
    return when (imageReference) {
        is ImageReference.ServerImage -> imageReference.fallback
        else -> null
    }
}

@Composable
private fun InspectionImageInternal(
    modifier: Modifier,
    imageReference: ImageReference,
    accessibilityLabel: String,
    contentScale: ContentScale,
    tint: Color?
) {
    TWTheme {
        when (imageReference) {
            is ImageReference.ResImage -> {
                Image(
                    modifier = modifier,
                    painter = painterResource(imageReference.res),
                    contentDescription = accessibilityLabel,
                    contentScale = contentScale,
                    colorFilter = tint?.let { ColorFilter.tint(it) }
                )
            }

            is ImageReference.ServerImage -> {
                if (imageReference.fallback != null) {
                    Image(
                        modifier = modifier,
                        painter = painterResource(imageReference.fallback),
                        contentDescription = accessibilityLabel,
                        contentScale = contentScale,
                        colorFilter = tint?.let { ColorFilter.tint(it) }
                    )
                } else {
                    Image(
                        modifier = modifier,
                        painter = painterResource(Res.drawable.compose_multiplatform_logo),
                        contentDescription = accessibilityLabel,
                        contentScale = contentScale,
                        colorFilter = tint?.let { ColorFilter.tint(it) }
                    )
                }
            }
        }
    }
}