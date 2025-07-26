package com.example.multiplatform_paging_sample.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import com.example.multiplatform_paging_sample.mapViewController

@Composable
actual fun MapComponent() {
    UIKitViewController(
        factory = mapViewController,
        modifier = Modifier.fillMaxSize(),
    )
}