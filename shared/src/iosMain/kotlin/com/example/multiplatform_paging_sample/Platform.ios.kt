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
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

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