package com.example.multiplatform_paging_sample


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun WebView(modifier: Modifier, url: String) {

    openUrlInBrowser(url)

}

