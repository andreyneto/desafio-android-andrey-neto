package br.com.andreyneto.desafioandroid.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.andreyneto.desafioandroid.ResultWrapper
import br.com.andreyneto.desafioandroid.model.ComicDataWrapper
import br.com.andreyneto.desafioandroid.repository.MarvelRepository
import kotlinx.coroutines.launch

class ComicViewModel(
    private val repository: MarvelRepository
): ViewModel() {
    val action: MutableLiveData<Action> = MutableLiveData()

    sealed class Action {
        class ShowComic(val comic: ComicDataWrapper.Data.Result?): Action()
        class LoadMore(val list: List<ComicDataWrapper.Data.Result>): Action()
    }

    sealed class ViewAction {
        class GetComics(val characterId: String?): ViewAction()
    }

    fun dispatchViewAction(viewAction: ViewAction) {
        when(viewAction) {
            is ViewAction.GetComics -> {
                action.postValue(Action.LoadMore(emptyList()))
                getComic(viewAction.characterId)
            }
        }
    }

    private fun getComic(characterId: String?) {
        viewModelScope.launch {
            (action.value as Action.LoadMore?).let {
                when (val result = repository.getComics(characterId, it?.list?.size?:0)) {
                    is ResultWrapper.NetworkError -> {
                    }
                    is ResultWrapper.GenericError -> {
                    }
                    is ResultWrapper.Success -> {
                        val comics = (it?.list?:emptyList()) + result.value.data.results
                        if(comics.size == result.value.data.total.toInt()) {
                            action.postValue(Action.ShowComic(findExpensivest(comics)))
                        } else {
                            action.postValue(Action.LoadMore(comics))
                            getComic(characterId)
                        }
                    }
                }
            }
        }
    }

    private fun findExpensivest(comics: List<ComicDataWrapper.Data.Result>) = comics.maxByOrNull {
            it.prices.maxByOrNull {
                it.price
            }?.price ?: 0.0f
        }
}