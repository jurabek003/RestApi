package uz.turgunboyevjurabek.restapi.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.turgunboyevjurabek.restapi.Madels.TodoGet
import uz.turgunboyevjurabek.restapi.databinding.ItemRvBinding

class RvAdapter(val list: ArrayList<TodoGet>,val rvClick: RvClick):RecyclerView.Adapter<RvAdapter.Vh>() {
    inner class Vh(val itemRvBinding:ItemRvBinding):ViewHolder(itemRvBinding.root){
        fun onBind(todoGet: TodoGet,position: Int){
            itemRvBinding.tvSarlovha.text=todoGet.sarlavha
            itemRvBinding.tvBatafsil.text=todoGet.batafsil
            itemRvBinding.tvSana.text=todoGet.sana
            itemRvBinding.tvZarurlik.text=todoGet.zarurlik

            itemRvBinding.itemMenu.setOnClickListener {
                rvClick.rvClick(itemRvBinding.itemMenu,todoGet,position)
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }
}

// Qaysi itemning menyusi bosilganligini olish uchun :)
interface RvClick{
    fun rvClick(imageView: ImageView,todoGet: TodoGet,position: Int)
}

