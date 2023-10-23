package com.test.api.view


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.test.api.dataModel.CurrentPrice
import com.test.api.dataModel.CurrentPriceResult
import com.test.api.db.entity.InterestCoinEntity
import com.test.api.repository.DBRepository
import com.test.api.repository.NetWorkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val netWorkRepository = NetWorkRepository()
    private val dbRepository = DBRepository()
    private lateinit var currentPriceResultList: ArrayList<CurrentPriceResult>

    fun getCurrentCoinList() = viewModelScope.launch {

        val result = netWorkRepository.getCurrentCoinList()
//        Log.e("코인리스트 가져오기",result.toString())

        currentPriceResultList = ArrayList()

        //데이터 내가 원하는대로 가공하기
        for (coin in result.data) {
            try {
                val gson = Gson()
                val gsonTogson = gson.toJson(result.data.get(coin.key)) // 저장된 값들
//                Log.e("코인키", result.data.get(coin.key).toString())
                val gsonFromJson = gson.fromJson(gsonTogson, CurrentPrice::class.java)

                val curremtPriceResult = CurrentPriceResult(coin.key, gsonFromJson)
//                Log.e("코인테이터2",curremtPriceResult.toString())
                currentPriceResultList.add(curremtPriceResult)
//                Log.e("코인 저장", currentPriceResultList.toString())
            } catch (e: java.lang.Exception) {
            }
        }
        saveCoinList()
    }

    fun saveCoinList() =
        viewModelScope.launch(Dispatchers.IO) {
            for (coin in currentPriceResultList) {
                val interestCoinEntity = InterestCoinEntity(
                    0,
                    coin.coinName,
                    coin.coinInfo.opening_price,
                    coin.coinInfo.closing_price,
                    coin.coinInfo.min_price,
                    coin.coinInfo.max_price,
                    coin.coinInfo.units_traded,
                    coin.coinInfo.acc_trade_value,
                    coin.coinInfo.prev_closing_price,
                    coin.coinInfo.units_traded_24H,
                    coin.coinInfo.acc_trade_value_24H,
                    coin.coinInfo.fluctate_24H,
                    coin.coinInfo.fluctate_rate_24H,
                )

                //저장하기
                interestCoinEntity.let {
                    dbRepository.insertInterestCoinData(it)
                }
            }
        }
}