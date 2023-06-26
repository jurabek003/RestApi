package uz.turgunboyevjurabek.restapi.ViewMadel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.restapi.Madels.TodoGet
import uz.turgunboyevjurabek.restapi.Madels.TodoPostRequest
import uz.turgunboyevjurabek.restapi.Madels.TodoPostRespons
import uz.turgunboyevjurabek.restapi.Network.ApiClient
import uz.turgunboyevjurabek.restapi.Repozitore.TodoRepozitory
import uz.turgunboyevjurabek.restapi.Utils.Resourse

class ViewModel(val todoRepozitory: TodoRepozitory):ViewModel() {
    private val liveData=MutableLiveData<Resourse<List<TodoGet>>>()

    fun getAllTodo():MutableLiveData<Resourse<List<TodoGet>>>{
        viewModelScope.launch {
            liveData.postValue(Resourse.loading("loading"))
           try {
               coroutineScope {
                   val list=async {
                       todoRepozitory.getAllItem()
                   }.await()
                   liveData.postValue(Resourse.success(list))
               }
           }catch (e:Exception){
               liveData.postValue(Resourse.error(e.message))
           }
        }
        return liveData
    }

    private val postLiveData=MutableLiveData<Resourse<TodoPostRespons>>()
    fun addPost(todoPostRequest: TodoPostRequest):MutableLiveData<Resourse<TodoPostRespons>>{
        postLiveData.postValue(Resourse.loading("loading..."))
        viewModelScope.launch {
            try {
                coroutineScope {
                    val response=async {
                        todoRepozitory.postItem(todoPostRequest)
                    }.await()
                    postLiveData.postValue(Resourse.success(response))
                    getAllTodo()
                }
            }catch (e:Exception){
                postLiveData.postValue(Resourse.error(e.message))
            }
        }
        return postLiveData
    }

    private val putLiveData=MutableLiveData<Resourse<TodoPostRespons>>()
    fun editItem(id:Int,todoPostRequest: TodoPostRequest):MutableLiveData<Resourse<TodoPostRespons>>{
        putLiveData.postValue(Resourse.loading("loading..."))
        viewModelScope.launch {
            try {
                coroutineScope {
                    val edit=async {
                        todoRepozitory.putItem(id, todoPostRequest)
                    }.await()
                    putLiveData.postValue(Resourse.success(edit))
                    getAllTodo()
                }
            }catch (e:java.lang.Exception){
                putLiveData.postValue(Resourse.error(e.message))
            }
        }
        return putLiveData
    }

    fun deleteItem(id: Int){
        viewModelScope.launch {
            try {
                coroutineScope {
                    launch {
                        todoRepozitory.deleteItem(id)
                    }
                    getAllTodo()
                }
            }catch (e:java.lang.Exception){

            }
        }
    }


}