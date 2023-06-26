package uz.turgunboyevjurabek.restapi.Repozitore

import uz.turgunboyevjurabek.restapi.Madels.TodoPostRequest
import uz.turgunboyevjurabek.restapi.Network.ApiServis

class TodoRepozitory(val apiServis: ApiServis) {
    suspend fun getAllItem()=apiServis.getAllItem()
    suspend fun postItem(todoPostRequest: TodoPostRequest)=apiServis.postItem(todoPostRequest)
    suspend fun putItem(id:Int,todoPostRequest: TodoPostRequest)=apiServis.putItem(id,todoPostRequest)
    suspend fun deleteItem(id: Int)=apiServis.deleteItem(id)


}