package io.github.rubenquadros.timetowish.ui.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import io.github.rubenquadros.timetowish.ui.TWTheme

/**
 * @see [io.github.rubenquadros.timetowish.ui.preview.text.TWTextPreview]
 */
@Composable
fun TWText(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = TWTheme.colors.onSurface,
    textStyle: TextStyle = TWTheme.typography.bodyLarge,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight = FontWeight.Normal,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        modifier = modifier,
        text = text,
        color = textColor,
        style = textStyle,
        textAlign = textAlign,
        fontWeight = fontWeight,
        minLines = minLines,
        maxLines = maxLines
    )
}