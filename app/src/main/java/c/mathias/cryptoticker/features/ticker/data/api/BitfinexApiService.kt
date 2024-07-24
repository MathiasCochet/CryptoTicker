package c.mathias.cryptoticker.features.ticker.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface BitfinexApiService {

    @GET("v2/tickers")
    fun getTickers(
        @Query("tickers") tickers: List<String>
    ): List<List<Any?>>
}