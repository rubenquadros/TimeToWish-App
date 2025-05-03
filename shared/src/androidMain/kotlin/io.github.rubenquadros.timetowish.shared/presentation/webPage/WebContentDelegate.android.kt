package io.github.rubenquadros.timetowish.shared.presentation.webPage

import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import io.github.rubenquadros.timetowish.core.activity.ActivityHolder

actual fun displayContent(url: String) {
    ActivityHolder.getActivityContext()?.let { ctx ->
        try {
            val customTabsIntent = CustomTabsIntent.Builder()
                .setShowTitle(true)
                .build()

            customTabsIntent.launchUrl(ctx, url.toUri())
        } catch (e: Exception) {
            // Fallback to browser
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            ctx.startActivity(intent)
        }
    }
}