package io.github.rubenquadros.timetowish.feature.login.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import io.github.rubenquadros.timetowish.feature.login.resources.Res
import io.github.rubenquadros.timetowish.feature.login.resources.login_loading_accessibility_label
import io.github.rubenquadros.timetowish.ui.TWTheme
import io.github.rubenquadros.timetowish.ui.loader.TWSkeletonLoader
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LoginScreenLoading() {
    val a11yLabel = stringResource(Res.string.login_loading_accessibility_label)

    Column(
        modifier = Modifier
            .padding(TWTheme.spacings.space4)
            .fillMaxSize()
            .semantics(mergeDescendants = true) {
                contentDescription = a11yLabel
            },
        verticalArrangement = Arrangement.spacedBy(TWTheme.spacings.space4),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //logo
        TWSkeletonLoader(
            variant = TWSkeletonLoader.Variant.Box(
                width = TWTheme.spacings.space16,
                height = TWTheme.spacings.space16
            )
        )

        //title
        TWSkeletonLoader(
            variant = TWSkeletonLoader.Variant.SingleLine
        )

        //description
        TWSkeletonLoader(
            variant = TWSkeletonLoader.Variant.ThreeLines
        )

        Spacer(modifier = Modifier.height(TWTheme.spacings.space2))

        //button
        TWSkeletonLoader(
            modifier = Modifier.fillMaxWidth().height(TWTheme.spacings.space8),
            variant = TWSkeletonLoader.Variant.Box()
        )

        Spacer(modifier = Modifier.height(TWTheme.spacings.space2))

        //terms and conditions
        TWSkeletonLoader(
            variant = TWSkeletonLoader.Variant.ThreeLines
        )

        //copyright
        TWSkeletonLoader(
            variant = TWSkeletonLoader.Variant.TwoLines
        )
    }
}