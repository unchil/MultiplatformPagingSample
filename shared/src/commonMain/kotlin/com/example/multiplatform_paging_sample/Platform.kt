package com.example.multiplatform_paging_sample

import io.ktor.client.HttpClient


interface Platform {
    val name: String


}

expect fun getPlatform(): Platform

expect fun getClient(): HttpClient

expect fun openUrlInBrowser(url: String, context:Any? = null)
