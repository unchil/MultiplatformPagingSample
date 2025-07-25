package com.example.multiplatform_paging_sample

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MultiplatformPagingSample",
    ) {

        AppPagingSample(
            onExploreItemClicked= {
                    repositories ->
                openUrlInBrowser(repositories.html_url)

            }
        )

    }
}