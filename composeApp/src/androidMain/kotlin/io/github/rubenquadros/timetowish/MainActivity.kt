package io.github.rubenquadros.timetowish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import io.github.rubenquadros.timetowish.core.activity.ActivityHolder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            TWApp()
        }.also {
            ActivityHolder.setActivityContext(context = this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityHolder.clear()
    }
}