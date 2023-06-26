package uz.turgunboyevjurabek.restapi.Madels


import com.google.gson.annotations.SerializedName

data class TodoPostRequest(
    @SerializedName("sarlavha")
    val sarlavha: String,
    @SerializedName("batafsil")
    val batafsil: String,
    @SerializedName("oxirgi_muddat")
    val oxirgiMuddat: String,
    @SerializedName("zarurlik")
    val zarurlik: String,
    @SerializedName("bajarildi")
    val bajarildi: Boolean,
)