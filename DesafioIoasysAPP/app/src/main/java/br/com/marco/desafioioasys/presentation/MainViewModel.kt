package br.com.marco.desafioioasys.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.marco.desafioioasys.data.model.AuthenticationResponse
import br.com.marco.desafioioasys.domain.ExecuteLoginUseCase
import br.com.marco.desafioioasys.util.Constants
import kotlinx.coroutines.launch
import retrofit2.Response



class MainViewModel (
    private val executeLoginUseCase: ExecuteLoginUseCase
        ) : ViewModel(){


    private val _authenticationState = MutableLiveData<AuthState>()
    val authenticationState: LiveData<AuthState> = _authenticationState


    fun executeLogin(email: String, password: String) {
        viewModelScope.launch {
            val response = executeLoginUseCase(email, password)
            if (response.isSuccessful) {
                val (token: String, client: String, uid: String) = getHeaders(response)
                _authenticationState.value = AuthState.Authenticated(token, uid, client)
            } else {
                _authenticationState.value = AuthState.Unauthenticated
            }
        }
    }

    sealed class AuthState {
        class Authenticated(val token: String, val client: String, val uid: String) : AuthState()
        object Unauthenticated : AuthState()
    }

    private fun getHeaders(response: Response<AuthenticationResponse>): Triple<String, String, String> {
        val token: String = response.headers()[Constants.ACCESS_TOKEN].toString()
        val uid: String = response.headers()[Constants.UID].toString()
        val client: String = response.headers()[Constants.CLIENT].toString()
        return Triple(token,uid, client)
    }

    fun isValidEmail(email: String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean{
        return password.isNotEmpty()
    }


}