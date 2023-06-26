package uz.turgunboyevjurabek.restapi.Madels


import com.google.gson.annotations.SerializedName

data class TodoPostRespons(
    @SerializedName("bajarildi")
    val bajarildi: Boolean,
    @SerializedName("batafsil")
    val batafsil: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("oxirgi_muddat")
    val oxirgiMuddat: String,
    @SerializedName("sana")
    val sana: String,
    @SerializedName("sarlavha")
    val sarlavha: String,
    @SerializedName("zarurlik")
    val zarurlik: String

)