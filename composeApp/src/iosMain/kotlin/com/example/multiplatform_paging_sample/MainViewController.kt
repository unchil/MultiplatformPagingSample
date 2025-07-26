package com.example.multiplatform_paging_sample

import androidx.compose.ui.window.ComposeUIViewController
import com.example.multiplatform_paging_sample.components.MapComponent
import platform.UIKit.UIViewController

/*
fun MainViewController() = ComposeUIViewController {

//    App()

/*
    AppPagingSample(
        onExploreItemClicked= { repositories ->
            openUrlInBrowser(repositories.html_url)
        }
    )

 */

    /*
    UIKitView(
        factory = {
            WKWebView()
        },
        update = { webView ->
            webView.loadRequest(NSURLRequest(uRL = NSURL(string = "https://www.google.com")))
        },
        modifier =  Modifier.fillMaxSize()
    )
     */


}

 */

fun MainViewController(
    mapUIViewController: () -> UIViewController
) = ComposeUIViewController {
    mapViewController = mapUIViewController
    MapComponent()
    //App()
}

lateinit var mapViewController: () -> UIViewController