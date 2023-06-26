package uz.turgunboyevjurabek.restapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs
import uz.turgunboyevjurabek.restapi.Adapter.RvAdapter
import uz.turgunboyevjurabek.restapi.Adapter.RvClick
import uz.turgunboyevjurabek.restapi.Madels.TodoGet
import uz.turgunboyevjurabek.restapi.Madels.TodoPostRequest
import uz.turgunboyevjurabek.restapi.Network.ApiClient
import uz.turgunboyevjurabek.restapi.Repozitore.TodoRepozitory
import uz.turgunboyevjurabek.restapi.Utils.Resourse
import uz.turgunboyevjurabek.restapi.Utils.Status
import uz.turgunboyevjurabek.restapi.ViewMadel.ViewModel
import uz.turgunboyevjurabek.restapi.ViewMadel.ViewModelFactory
import uz.turgunboyevjurabek.restapi.databinding.ActivityMainBinding
import uz.turgunboyevjurabek.restapi.databinding.DialogItemBinding
import uz.turgunboyevjurabek.restapi.databinding.ItemRvBinding

class MainActivity : AppCompatActivity(),RvClick {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var viewModel: ViewModel
    lateinit var rvAdapter: RvAdapter
    lateinit var todoRepozitory: TodoRepozitory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        todoRepozitory= TodoRepozitory(ApiClient.getApiServis())
        viewModel=ViewModelProvider(this,ViewModelFactory(todoRepozitory)).get(ViewModel::class.java)

        viewModel.getAllTodo().observe(this){
            when(it.status){
                Status.LOADING->{
                    //Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR->{
                    Toast.makeText(this, "error:: ${it.massage}", Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS->{
                    //Toast.makeText(this, "Uraaaaaaa", Toast.LENGTH_SHORT).show()
                    rvAdapter= RvAdapter(it.data as ArrayList<TodoGet>,this)
                    binding.rv.adapter=rvAdapter
                    rvAdapter.notifyDataSetChanged()
                }
            }
        }

        binding.btnAdd.setOnClickListener {
            val dialog= MaterialAlertDialogBuilder(this).create()
            val dialogItemBinding=DialogItemBinding.inflate(layoutInflater)
            dialog.setView(dialogItemBinding.root)
            dialogItemBinding.apply {
                btnSave.setOnClickListener {
                    val todoPostRequest=TodoPostRequest(
                        edtSarlovha.text.toString().trim(),
                        edtBatafsil.text.toString().trim(),
                        edtOxirgiMuddat.text.toString().trim(),
                        spinnerZarurlik.selectedItem.toString().trim(),
                        true,
                    )
                    viewModel.addPost(todoPostRequest).observe(this@MainActivity){
                        when(it.status){
                            Status.LOADING->{
                                dialogItemBinding.dialogProgress.visibility=View.VISIBLE
                                Toast.makeText(this@MainActivity, "${Status.LOADING} 🔃", Toast.LENGTH_SHORT).show()
                            }
                            Status.ERROR->{
                                dialogItemBinding.dialogProgress.visibility=View.GONE
                                Toast.makeText(this@MainActivity, "Kalla bomi 😡 ${it.massage}", Toast.LENGTH_SHORT).show()
                            }
                            Status.SUCCESS->{
                                Toast.makeText(this@MainActivity, "${it.data?.sarlavha}  nomli todo ${it.data?.id} inchi id ga saqlandi 😊", Toast.LENGTH_SHORT).show()
                                dialog.cancel()
                            }

                        }
                    }
                }

            }
            dialog.show()
        }
    }
    //RecakleView ning bosil gan itemni oloish uchun :)
    override fun rvClick(imageView: ImageView, todoGet: TodoGet, position: Int) {
        val popupMenu=PopupMenu(this,imageView)
        popupMenu.inflate(R.menu.popap_menu)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_edit ->{

                   editRvItem(todoGet)
                }
                R.id.menu_delet->{
                    deleteRvItem(todoGet.id)
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }
    private fun editRvItem(todoGet: TodoGet) {
        val dialogs=BottomSheetDialog(this)
        val dialogItemBinding=DialogItemBinding.inflate(layoutInflater)
        dialogs.setContentView(dialogItemBinding.root)
        dialogItemBinding.edtSarlovha.setText(todoGet.sarlavha)
        dialogItemBinding.edtBatafsil.setText(todoGet.batafsil)
        dialogItemBinding.edtOxirgiMuddat.setText(todoGet.oxirgiMuddat)
        when(todoGet.zarurlik){
            "shart"->{
                dialogItemBinding.spinnerZarurlik.setSelection(0)
            }
            "foydali"->{
                dialogItemBinding.spinnerZarurlik.setSelection(1)
            }
            "hayot_mamot"->{
                dialogItemBinding.spinnerZarurlik.setSelection(2)
            }
            "tavsiya"->{
                dialogItemBinding.spinnerZarurlik.setSelection(3)
            }
        }
        //popupMenu.dismiss()
        dialogs.show()

        dialogItemBinding.btnSave.setOnClickListener {
            val todoPostRequest=TodoPostRequest(
                dialogItemBinding.edtSarlovha.text.toString().trim(),
                dialogItemBinding.edtBatafsil.text.toString().trim(),
                dialogItemBinding.edtOxirgiMuddat.text.toString().trim(),
                dialogItemBinding.spinnerZarurlik.selectedItem.toString(),
                true
            )
            viewModel.editItem(todoGet.id,todoPostRequest).observe(this){
                when(it.status){
                    Status.LOADING->{
                        dialogItemBinding.dialogProgress.visibility=View.VISIBLE
                        Toast.makeText(this, "Editing...", Toast.LENGTH_SHORT).show()
                    }
                    Status.ERROR->{
                        dialogItemBinding.dialogProgress.visibility=View.GONE
                        Toast.makeText(this, "${it.massage}", Toast.LENGTH_SHORT).show()
                    }
                    Status.SUCCESS-> {
                        Toast.makeText(this, "o'zgartirilidi :)", Toast.LENGTH_SHORT).show()
                        dialogs.cancel()
                    }
                }
            }
        }


    }
    private fun deleteRvItem(id:Int){
        viewModel.deleteItem(id)
    }
}