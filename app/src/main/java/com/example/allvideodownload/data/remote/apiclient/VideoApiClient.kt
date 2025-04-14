package com.example.allvideodownload.data.remote.apiclient

import com.example.allvideodownload.data.remote.api.api
import com.example.allvideodownload.data.remote.constant.Constant.TIMEOUT
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object VideoApiClient {
    @OptIn(ExperimentalSerializationApi::class)
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                    prettyPrint = true
                }
            )
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        }
        install(HttpTimeout) {
            socketTimeoutMillis = TIMEOUT
            requestTimeoutMillis = TIMEOUT
            connectTimeoutMillis = TIMEOUT
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun VideosDownload(url: String): api {
        val response = client.post("https://social-download-all-in-one.p.rapidapi.com/v1/social/autolink") {
            headers {
                append("Content-Type", "application/json")
                append("x-rapidapi-host", "social-download-all-in-one.p.rapidapi.com")
                append("x-rapidapi-key", "972c1a1e5amsh04dc398b681e16fp1de36fjsnd0efe83b3f8a")
            }
            body = TextContent(
                """{"url":"$url"}""",
                contentType = ContentType.Application.Json
            )
        }
        return response.body()
    }
}

