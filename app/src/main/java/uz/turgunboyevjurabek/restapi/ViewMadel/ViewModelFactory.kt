package uz.turgunboyevjurabek.restapi.ViewMadel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.turgunboyevjurabek.restapi.Repozitore.TodoRepozitory

class ViewModelFactory(val todoRepozitory: TodoRepozitory):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(uz.turgunboyevjurabek.restapi.ViewMadel.ViewModel::class.java)){
            return uz.turgunboyevjurabek.restapi.ViewMadel.ViewModel(todoRepozitory) as T
        }
     throw IllegalArgumentException("error")
    }
}