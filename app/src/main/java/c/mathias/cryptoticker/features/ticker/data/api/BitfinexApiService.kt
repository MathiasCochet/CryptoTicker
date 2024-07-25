package c.mathias.cryptoticker.features.ticker.data.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BitfinexApiService {

    @GET("tickers")
    suspend fun getTickers(
        @Query("symbols") tickers: String
    ): List<List<Any?>>
}