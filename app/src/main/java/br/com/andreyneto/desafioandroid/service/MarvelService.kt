package br.com.andreyneto.desafioandroid.service

import br.com.andreyneto.desafioandroid.model.CharacterDataWrapper
import br.com.andreyneto.desafioandroid.model.ComicDataWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {
    @GET("characters")
    suspend fun fetchCharacters(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String?,
        @Query("orderBy") orderBy: String = "name",
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): CharacterDataWrapper

    @GET("characters/{id}/comics")
    suspend fun getComics(
        @Path("id") id: String?,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String?,
        @Query("orderBy") orderBy: String = "title",
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): ComicDataWrapper
}