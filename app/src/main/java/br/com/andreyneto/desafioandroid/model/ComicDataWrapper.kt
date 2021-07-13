package br.com.andreyneto.desafioandroid.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ComicDataWrapper(
    @SerializedName("data")
    val `data`: Data
) {
    data class Data(
        @SerializedName("results")
        val results: List<Result>,
        @SerializedName("total")
        val total: String
    ) {
        data class Result(
            @SerializedName("title")
            val title: String,
            @SerializedName("description")
            val description: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("prices")
            val prices: List<Price>,
            @SerializedName("thumbnail")
            val thumbnail: Thumbnail
        ) {

            data class Thumbnail(
                @SerializedName("extension")
                val extension: String,
                @SerializedName("path")
                val path: String
            )
        }

            data class Price(
                @SerializedName("price")
                val price: Float,
                @SerializedName("type")
                val type: String
            )
        }
    }