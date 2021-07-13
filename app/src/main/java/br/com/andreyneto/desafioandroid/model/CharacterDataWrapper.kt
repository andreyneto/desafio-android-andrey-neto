package br.com.andreyneto.desafioandroid.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CharacterDataWrapper(
    @SerializedName("data")
    val `data`: Data
): Serializable {
    data class Data(
        @SerializedName("results")
        val results: List<Result>
    ) {
        data class Result(
            @SerializedName("description")
            val description: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("thumbnail")
            val thumbnail: Thumbnail,
        ): Serializable {

            data class Thumbnail(
                @SerializedName("extension")
                val extension: String,
                @SerializedName("path")
                val path: String
            ): Serializable
        }
    }
}