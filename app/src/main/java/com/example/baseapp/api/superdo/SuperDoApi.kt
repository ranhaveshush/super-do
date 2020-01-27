package com.example.baseapp.api.superdo

import com.example.baseapp.BuildConfig
import com.example.baseapp.vo.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import okhttp3.*
import org.json.JSONObject

class SuperDoApi {
    private val baseUrl = BuildConfig.SUPERMARKET_URL

    fun listGroceries(): Flow<Resource<GroceryResponse>> = channelFlow {
        val client = OkHttpClient.Builder().build()

        val request = Request.Builder()
            .url(baseUrl)
            .build()

        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response?) {
                println("OPEN:")

                channel.offer(Resource.loading())
            }

            override fun onMessage(webSocket: WebSocket?, text: String?) {
                println("MESSAGE: $text")

                if (!text.isNullOrEmpty()) {
                    val jsonObject = JSONObject(text)
                    val grocery = GroceryResponse.fromJson(jsonObject)

                    channel.offer(Resource.success(grocery))
                }
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                println("CLOSE: $code $reason")

                webSocket.close(1000, null)
            }

            override fun onFailure(
                webSocket: WebSocket?,
                t: Throwable,
                response: Response?
            ) {
                println("FAILURE: ${t.message}")

                val resource = Resource.error<GroceryResponse>(t.message ?: "WebSocket Failure", t)
                channel.offer(resource)
            }
        }

        val webSocket = client.newWebSocket(request, listener)

        awaitClose {
            println("Await Close!")

            webSocket.cancel()
        }
    }
}
