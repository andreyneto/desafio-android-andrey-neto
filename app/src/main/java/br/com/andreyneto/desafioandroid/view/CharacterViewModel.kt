package br.com.andreyneto.desafioandroid.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.andreyneto.desafioandroid.ResultWrapper
import br.com.andreyneto.desafioandroid.model.CharacterDataWrapper
import br.com.andreyneto.desafioandroid.repository.MarvelRepository
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val repository: MarvelRepository
): ViewModel() {
    val action: MutableLiveData<Action> = MutableLiveData()

    sealed class Action {
        class ShowCharacters(val characters: List<CharacterDataWrapper.Data.Result>): Action()
    }

    sealed class ViewAction {
        object FetchCharacters: ViewAction()
    }

    fun dispatchViewAction(viewAction: ViewAction) {
        when(viewAction) {
            is ViewAction.FetchCharacters -> fetchCharacters()
        }
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            (action.value as Action.ShowCharacters?).let {
                when (val result = repository.fetchCharacters(it?.characters?.size?:0)) {
                    is ResultWrapper.NetworkError -> {
                    }
                    is ResultWrapper.GenericError -> {
                    }
                    is ResultWrapper.Success -> {
                        val characters = (it?.characters?: emptyList()) + result.value.data.results
                        action.postValue(Action.ShowCharacters(characters))
                    }
                }
            }
        }
    }
}