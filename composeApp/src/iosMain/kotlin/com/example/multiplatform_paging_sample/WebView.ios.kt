package com.example.multiplatform_paging_sample

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView

import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView

@Composable
actual fun WebView(modifier: Modifier, url: String) {

    //openUrlInBrowser(url)

    val nsUrl = NSURL(string = url)
    val nsRequest = NSURLRequest(uRL = nsUrl)

    UIKitView(
        modifier = modifier,
        factory = { WKWebView() },
        update = { webView ->
            webView.loadRequest(nsRequest)
        }
    )



}