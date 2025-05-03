package io.github.rubenquadros.timetowish.feature.login.ui.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.rubenquadros.timetowish.feature.login.presentation.ui.TermsAndPolicyBottomSheet
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.preview.TWPreview
import io.github.rubenquadros.timetowish.ui.preview.TWPreviewTheme

@TWPreview
@Composable
private fun TermsAndPrivacyBottomSheetPreview() {
    TWPreviewTheme {
        Box(modifier = Modifier.fillMaxWidth().background(TWTheme.colors.surfaceContainer)) {
            TermsAndPolicyBottomSheet { }
        }
    }
}