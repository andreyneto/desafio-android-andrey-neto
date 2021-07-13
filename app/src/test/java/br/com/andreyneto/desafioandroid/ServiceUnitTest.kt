package br.com.andreyneto.desafioandroid

import br.com.andreyneto.desafioandroid.model.CharacterDataWrapper
import br.com.andreyneto.desafioandroid.model.ComicDataWrapper
import br.com.andreyneto.desafioandroid.service.MarvelService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ServiceUnitTest {

    private val api = mock<MarvelService>()

    private val service = MockService(api)

    @Test
    fun fetchCharactersTest() = runBlocking {
        val expectedCharacters = CharacterDataWrapper(CharacterDataWrapper.Data(emptyList()))

        whenever(api.fetchCharacters(any(), any(), any(), any(), any(), any())).thenReturn(expectedCharacters)

        val characters = service.fetchCharacters()

        assertEquals(characters, expectedCharacters)
    }

    @Test
    fun getComicsTest() = runBlocking {
        val expectedComics = ComicDataWrapper(ComicDataWrapper.Data(emptyList(),""))

        whenever(api.getComics(any(), any(), any(), any(), any(), any(), any())).thenReturn(expectedComics)

        val comics = service.getComics()

        assertEquals(comics, expectedComics)
    }

    inner class MockService(private val service: MarvelService) {
        suspend fun fetchCharacters(): CharacterDataWrapper {
            return service.fetchCharacters("ts", "key", "hash")
        }

        suspend fun getComics(): ComicDataWrapper {
            return service.getComics("id", "ts", "key", "hash")
        }
    }
}