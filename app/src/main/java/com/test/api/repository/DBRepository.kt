package com.test.api.repository

import com.test.api.App
import com.test.api.db.CoinPriceDatabase
import com.test.api.db.entity.InterestCoinEntity

class DBRepository {

    val context = App.context()
    val db = CoinPriceDatabase.getDatabase(context)

    // InterestCoin
    // 전체 코인 데이터 가져오기
    fun getAllInterestCoinData() = db.interestCoinDAO().getAllData()

    // 코인 데이터 넣기
    fun insertInterestCoinData(interestCoinEntity: InterestCoinEntity) =
        db.interestCoinDAO().insert(interestCoinEntity)

    // 코인 데이터 업데이트
//    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) =
//        db.interestCoinDAO().update(interestCoinEntity)
//
//    // 사용자가 관심있어한 코인만 가져오기
//    fun getAllInterestSelectedCoinData() = db.interestCoinDAO().getSelectedData()
}
