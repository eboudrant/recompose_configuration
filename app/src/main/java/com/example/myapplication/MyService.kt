package com.example.myapplication

import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

// shortcut to save globally the state
private var added = false

internal class MyService(
    private val componentActivity: ComponentActivity,
) {

    private val composeView by lazy {
        // Lazily create and add the view to hierarchy
        ComposeView(componentActivity).apply {
            setupAndAddView(componentActivity)
            setContent {
                // This content will not recompose on orientation change
                Surface(color = Color.Red) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("Hello World")
                    }
                }
            }
        }
    }

    init {
        if (added) {
            //
            composeView
        }
    }

    fun showModalScreen() {
        composeView
        added = true
    }

    private fun ComposeView.setupAndAddView(componentActivity: ComponentActivity) {
        setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnLifecycleDestroyed(
                componentActivity
            )
        )
        setViewTreeLifecycleOwner(componentActivity)
        setViewTreeSavedStateRegistryOwner(componentActivity)
        setViewTreeViewModelStoreOwner(componentActivity)
        componentActivity.findViewById<ViewGroup>(android.R.id.content).addView(this)
    }
}