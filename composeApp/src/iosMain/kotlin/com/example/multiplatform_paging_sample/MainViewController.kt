package com.example.multiplatform_paging_sample

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    AppPagingSample(
        onExploreItemClicked= { repositories ->
            openUrlInBrowser(repositories.html_url)
        }
    )

}