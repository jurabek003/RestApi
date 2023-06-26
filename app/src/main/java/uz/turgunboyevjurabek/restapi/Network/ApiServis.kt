package uz.turgunboyevjurabek.restapi.Network

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import uz.turgunboyevjurabek.restapi.Madels.TodoGet
import uz.turgunboyevjurabek.restapi.Madels.TodoPostRequest
import uz.turgunboyevjurabek.restapi.Madels.TodoPostRespons

interface ApiServis {
    @GET("plan/")
    suspend fun getAllItem() :ArrayList<TodoGet>

    @POST("plan/")
    suspend fun postItem(@Body todoPostRequest: TodoPostRequest):TodoPostRespons

    @PUT("plan/{id}/")
    suspend fun putItem(@Path("id") id:Int,@Body todoPostRequest: TodoPostRequest):TodoPostRespons

    @DELETE("plan/{id}/")
    suspend fun deleteItem(@Path("id") id: Int )

}