package aaa.bivizul.a29project.data.spbknet

import aaa.bivizul.a29project.data.model.Getspbk
import aaa.bivizul.a29project.data.model.Spbk
import aaa.bivizul.a29project.data.model.Sportsbook
import aaa.bivizul.a29project.data.spbkkutil.Spbkcon.URLBASE
import aaa.bivizul.a29project.data.spbkkutil.Spbkcon.URLSPBK
import aaa.bivizul.a29project.data.spbkkutil.Spbkcon.URLSPORTSBOOK
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class SpbkApi {

    val client = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
        install(HttpTimeout) {
            val timeout = 30000L
            connectTimeoutMillis = timeout
            requestTimeoutMillis = timeout
            socketTimeoutMillis = timeout
        }
    }

    private fun HttpRequestBuilder.base(path: String) {
        url {
            takeFrom(URLBASE)
            encodedPath = path
        }
    }

    suspend fun getSportsbook(): List<Sportsbook> {
        val url = URLSPORTSBOOK
        val response = client.get{ base(url)}

        println("SpbkApi getSportsbook : ${response.status}")

        val body = response.body<List<Sportsbook>>()
        return body
    }

    suspend fun getGetspbk(spbk: Spbk): Getspbk {
        val url = URLSPBK
        val response = client.post {
            base(url)
            contentType(ContentType.Application.Json)
            setBody(spbk)
        }

        println("SpbkApi getGetspbk : ${response.status}")

        val body = response.body<Getspbk>()
        return body
    }

}