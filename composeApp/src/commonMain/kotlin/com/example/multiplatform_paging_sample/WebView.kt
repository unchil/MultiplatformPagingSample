package com.example.multiplatform_paging_sample

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun WebView(
    modifier: Modifier = Modifier,
    url: String
)