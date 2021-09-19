package br.com.marco.desafioioasys.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.marco.desafioioasys.data.model.Enterprises
import br.com.marco.desafioioasys.domain.ListEnterprisesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel (
    private val listEnterprisesUseCase: ListEnterprisesUseCase
) : ViewModel(){

    private val _listEnterpriseState = MutableLiveData<State>()
    val listEnterpriseState: LiveData<State> = _listEnterpriseState

    fun getEnterpriseList(token:String, client:String, uid:String) {
        viewModelScope.launch {
            listEnterprisesUseCase(token,client,uid)
                .onStart {
                    _listEnterpriseState.postValue(State.Loading)
                }
                .catch {
                    _listEnterpriseState.postValue(State.Error(it))
                }
                .collect {
                    _listEnterpriseState.postValue(State.Success(it))
                }
        }
    }



    sealed class State {
        object Loading : State()
        data class Success(val list: Enterprises) : State()
        data class Error(val error: Throwable) : State()
    }

}