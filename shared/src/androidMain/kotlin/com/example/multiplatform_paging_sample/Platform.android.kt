package com.example.multiplatform_paging_sample

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getClient(): HttpClient =  HttpClient {

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

actual fun openUrlInBrowser(url: String, context: Any?) {
    context?.let {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            (it as Context).startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}