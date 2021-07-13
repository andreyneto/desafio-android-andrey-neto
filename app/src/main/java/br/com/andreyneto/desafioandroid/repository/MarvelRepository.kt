package br.com.andreyneto.desafioandroid.repository

import br.com.andreyneto.desafioandroid.ResultWrapper
import br.com.andreyneto.desafioandroid.md5
import br.com.andreyneto.desafioandroid.model.CharacterDataWrapper
import br.com.andreyneto.desafioandroid.model.ComicDataWrapper
import br.com.andreyneto.desafioandroid.safeApiCall
import br.com.andreyneto.desafioandroid.service.MarvelService
import kotlinx.coroutines.Dispatchers
import java.util.*

interface MarvelRepository {
    suspend fun fetchCharacters(count: Int): ResultWrapper<CharacterDataWrapper>
    suspend fun getComics(characterId: String?, count: Int): ResultWrapper<ComicDataWrapper>
}

class MarvelRepositoryImpl(
    private val marvelService: MarvelService
): MarvelRepository {
    override suspend fun fetchCharacters(count: Int) = safeApiCall(Dispatchers.IO) {
        val uuid = UUID.randomUUID().toString()
        val hash = md5(uuid, secret, apikey)
        marvelService.fetchCharacters(uuid, apikey, hash, offset = count)
    }

    override suspend fun getComics(characterId: String?, count: Int) = safeApiCall(Dispatchers.IO) {
        val uuid = UUID.randomUUID().toString()
        val hash = md5(uuid, secret, apikey)
        marvelService.getComics(characterId, uuid, apikey, hash, offset = count)
    }

    companion object {
        const val apikey = "204ca189ad175d8f654b9828e42a1dd7"
        const val secret = "4e3926fd88389f2f52cedbd5f1217b3df81d6181"
    }
}