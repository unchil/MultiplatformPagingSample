package com.example.multiplatform_paging_sample

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.awt.Desktop
import java.net.URI

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()


actual fun getClient(): HttpClient =  HttpClient {

    install(Logging){
        logger = Logger.DEFAULT
        level = LogLevel.ALL
    }

    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true          // JSON 로그를 예쁘게 출력
                isLenient = true            // 유연한 파싱 (따옴표 없는 키 등 허용)
                ignoreUnknownKeys = true    // 데이터 클래스 에 없는 키는 무시
                coerceInputValues = true
            }
        )
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 10000
        connectTimeoutMillis = 3000
        socketTimeoutMillis = 3000
    }
}

actual fun openUrlInBrowser(url: String, context:Any?) {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
        try {
            Desktop.getDesktop().browse(URI(url))
        } catch (e: Exception) {
            System.err.println("Failed to open URL in browser: $url, Error: ${e.message}")
            e.printStackTrace()
        }
    } else {
        System.err.println("Desktop BROWSE action is not supported on this platform.")
    }
}