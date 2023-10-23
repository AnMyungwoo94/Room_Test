package com.test.api.repository
import com.test.api.network.Api
import com.test.api.network.RetrofitInstance

class NetWorkRepository {

    private val client = RetrofitInstance.getInstance().create(Api::class.java)
    suspend fun getCurrentCoinList() = client.getCurrentCoinList()

}