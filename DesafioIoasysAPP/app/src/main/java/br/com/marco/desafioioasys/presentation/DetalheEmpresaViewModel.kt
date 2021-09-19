package br.com.marco.desafioioasys.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.marco.desafioioasys.data.model.Enterprises
import br.com.marco.desafioioasys.domain.ListEnterprisesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetalheEmpresaViewModel (
    private val listEnterprisesUseCase: ListEnterprisesUseCase
): ViewModel(){

    private val _enterpriseState = MutableLiveData<State>()
    val enterpriseState: LiveData<State> = _enterpriseState

    fun getEnterprise(token: String, client: String, uid: String) {
        viewModelScope.launch {
            listEnterprisesUseCase(token,client,uid)
                .onStart {
                    _enterpriseState.postValue(State.Loading)
                }
                .catch {
                    _enterpriseState.postValue(State.Error(it))
                }
                .collect {
                    _enterpriseState.postValue(State.Success(it))
                }
        }
    }

    sealed class State {
        object Loading : State()
        data class Success(val enterprise: Enterprises) : State()
        data class Error(val error: Throwable) : State()
    }


}