package com.example.multiplatform_paging_sample

import androidx.compose.ui.window.ComposeUIViewController
import com.example.multiplatform_paging_sample.components.MapComponent
import platform.UIKit.UIViewController

fun MainViewController(
    mapUIViewController: () -> UIViewController
) = ComposeUIViewController {
    mapViewController = mapUIViewController
    MapComponent()
    // App()
    // AppPagingSample( onExploreItemClicked= { repositories ->  openUrlInBrowser(repositories.html_url) } )

}

lateinit var mapViewController: () -> UIViewController