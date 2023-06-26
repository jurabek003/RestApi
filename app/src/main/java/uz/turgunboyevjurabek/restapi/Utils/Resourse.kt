package uz.turgunboyevjurabek.restapi.Utils

import uz.turgunboyevjurabek.restapi.Network.ApiServis

data class Resourse<out T>(val status: Status,val data:T?,val massage:String?) {

    companion object{
        fun <T>success(data:T):Resourse<T>{
            return Resourse(Status.SUCCESS,data,null)
        }

        fun <T>error(massage: String?):Resourse<T>{
            return Resourse(Status.ERROR,null,massage)
        }

        fun <T>loading(massage: String?):Resourse<T>{
            return Resourse(Status.LOADING,null,null)
        }

    }
}