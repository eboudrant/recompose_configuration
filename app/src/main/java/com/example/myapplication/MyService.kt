package com.example.myapplication

import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

private var added = false

internal class MyService(
    private val componentActivity: ComponentActivity,
) {

    private val composeView by lazy {
        ComposeView(componentActivity).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(
                    componentActivity
                )
            )
            setViewTreeLifecycleOwner(componentActivity)
            setViewTreeSavedStateRegistryOwner(componentActivity)
            setViewTreeViewModelStoreOwner(componentActivity)
            componentActivity.findViewById<ViewGroup>(android.R.id.content).addView(this)
            setContent {
                // This compose view will never recompose on orientation change
                Surface(
                    color = Color.Red,
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("Hello World")
                    }
                }
                LaunchedEffect(Unit) {

                    delay(2.seconds)
                    componentActivity.recreate()
                }
            }
        }
    }

    init {
        if (added) {
            composeView
        }
    }

    fun show() {
        composeView
        added = true
    }
}