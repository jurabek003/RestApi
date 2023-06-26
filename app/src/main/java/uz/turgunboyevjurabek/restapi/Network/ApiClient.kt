package uz.turgunboyevjurabek.restapi.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    const val BASE_URL="http://plans1.pythonanywhere.com/"
    fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiServis():ApiServis{
        return getRetrofit().create(ApiServis::class.java)
    }
}