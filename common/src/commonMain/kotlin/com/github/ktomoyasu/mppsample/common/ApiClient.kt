package com.github.ktomoyasu.mppsample.common

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.URLProtocol
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

internal expect val coroutineDispatcher: CoroutineDispatcher

class ApiClient {
    companion object {
        private const val hostName = "api.github.com"
    }
    private val client = HttpClient()

    fun searchRepository(value: String, onSuccess: (RepositoryList) -> Unit, onError: (Exception) -> Unit) {
        Preference.put(KEY_LAST_SEARCH, value)
        GlobalScope.apply {
           launch(coroutineDispatcher) {
               try {
                  val result = client.get<String> {
                      url {
                          protocol = URLProtocol.HTTPS
                          host = hostName
                          path(listOf("search", "repositories"))
                          parameter(key = "q", value = "$value")
                          parameter(key = "sort", value = "stars")
                          parameter(key = "order", value = "desc")
                      }
                  }
                  val repos = Json.nonstrict.parse(RepositoryList.serializer(), result)
                  onSuccess(repos)
               } catch (e: Exception) {
                   onError(e)
               }
           }
        }
    }
}
