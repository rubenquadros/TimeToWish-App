package io.github.rubenquadros.timetowish.feature.home.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import io.github.rubenquadros.timetowish.core.session.CurrentUser
import io.github.rubenquadros.timetowish.feature.home.domain.entity.Event
import io.github.rubenquadros.timetowish.feature.home.domain.entity.HomeEntity
import io.github.rubenquadros.timetowish.feature.home.presentation.ui.HomeContent
import io.github.rubenquadros.timetowish.ui.preview.TWPreview
import kotlinx.collections.immutable.persistentListOf

@TWPreview
@Composable
private fun HomeContentPreview(
    @PreviewParameter(HomeEntityPreviewParameterProvider::class) homeEntity: HomeEntity
) {
    HomeScreenPreview {
        HomeContent(homeEntity)
    }
}

private class HomeEntityPreviewParameterProvider : CollectionPreviewParameterProvider<HomeEntity>(
    listOf(
        HomeEntity(currentUser = null, todayEvents = null),
        HomeEntity(
            currentUser = CurrentUser("123", "Test Name", "test@gmail.com", "profile.png", false),
            todayEvents = persistentListOf()
        ),
        HomeEntity(
            currentUser = CurrentUser("123", "Test Name", "test@gmail.com", "profile.png", true),
            todayEvents = persistentListOf()
        ),
        HomeEntity(
            currentUser = CurrentUser("123", "Test Name", "test@gmail.com", "profile.png", true),
            todayEvents = persistentListOf(
                Event("456", "123", "12-02-1995", "Test event 1", "Test description 1", 12, 2, null, null)
            )
        ),
        HomeEntity(
            currentUser = CurrentUser("123", "Test Name", "test@gmail.com", "profile.png", true),
            todayEvents = persistentListOf(
                Event("456", "123", "14-02-1995", "Test event 1", "Test description 1", 14, 2, null, null),
                Event("789", "123", "14-02-1995", "Test event 2", "Test description 2", 14, 2, null, null)
            )
        )
    )
)