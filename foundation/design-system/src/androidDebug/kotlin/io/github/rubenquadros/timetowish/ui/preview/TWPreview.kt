package io.github.rubenquadros.timetowish.ui.preview

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark

@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FUNCTION
)
@PreviewLightDark
@Preview(name = "Phone")
@Preview(name = "Phone Large Text", fontScale = 1.5f)
@Preview(name = "Small Phone", widthDp = 360, heightDp = 640)
annotation class TWPreview