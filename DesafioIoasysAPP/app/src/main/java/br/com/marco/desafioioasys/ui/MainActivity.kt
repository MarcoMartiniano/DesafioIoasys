package br.com.marco.desafioioasys.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.core.widget.addTextChangedListener
import br.com.marco.desafioioasys.R
import br.com.marco.desafioioasys.presentation.MainViewModel
import br.com.marco.desafioioasys.util.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel
import br.com.marco.desafioioasys.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<MainViewModel>()
    private var emailUser = ""
    private var passwordUser = ""
    private var controleVisibility = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindObserves()
        listeners()
        //setButtonEffects(false)
    }


    //Função para chamar os listeners
    private fun listeners(){

            binding.btEntrar.setOnClickListener {
            binding.loginProgressBar.visibility = View.VISIBLE
            binding.txtCredenciaisInvalidas.visibility = View.GONE

            //Descomente o codigo para capturar String do edtText
            //emailUser = binding.edtEmail.text.toString()
            //passwordUser = binding.edtSenha.text.toString()

            //Descomente o codigo caso queira logar sem precisar digitar $emailUser $passwordUser
            emailUser = "testeapple@ioasys.com.br"
            passwordUser = "12341234"

            viewModel.executeLogin(emailUser ,passwordUser)
        }

        binding.icVisibility.setOnClickListener {
            if(controleVisibility == 0){
                binding.edtSenha.transformationMethod = HideReturnsTransformationMethod.getInstance()
                val tam = binding.edtSenha.length()
                binding.edtSenha.setSelection(tam)
                binding.icVisibility.setImageResource(R.drawable.ic_visible_off)
                controleVisibility = 1
                return@setOnClickListener
            }else{
                binding.edtSenha.transformationMethod = PasswordTransformationMethod.getInstance()
                val tam = binding.edtSenha.length()
                binding.edtSenha.setSelection(tam)
                binding.icVisibility.setImageResource(R.drawable.ic_visible)
                controleVisibility = 0
                return@setOnClickListener
            }

        }
        binding.edtEmail.addTextChangedListener {
            //se for valido o email
            emailUser = binding.edtEmail.text.toString()
            passwordUser = binding.edtSenha.text.toString()
            if(viewModel.isValidEmail(emailUser) && viewModel.isValidPassword(passwordUser)){
                setButtonEffects(true)
            }else{
                setButtonEffects(false)

            }
            if(viewModel.isValidEmail(emailUser)){
                binding.icError.visibility = View.GONE
            }else{
                binding.icError.visibility = View.VISIBLE
            }
        }
        binding.edtSenha.addTextChangedListener{
            emailUser = binding.edtEmail.text.toString()
            passwordUser = binding.edtSenha.text.toString()
            if(viewModel.isValidEmail(emailUser) && viewModel.isValidPassword(passwordUser)){
                setButtonEffects(true)
                binding.icError.visibility = View.GONE
            }else{
                setButtonEffects(false)
                binding.icError.visibility = View.VISIBLE
            }
        }

    }

    //Função para chamar os observers
    private fun bindObserves() {
        viewModel.authenticationState.observe(this){
            when (it) {
                is MainViewModel.AuthState.Authenticated -> {
                    val token = it.token
                    val client = it.client
                    val uid = it.uid
                    val intent = Intent(this, HomeActivity::class.java)
                    val preference=getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                    val prefsEditor = preference.edit()
                    prefsEditor.putString(Constants.ACCESS_TOKEN, token)
                    prefsEditor.putString(Constants.CLIENT, client)
                    prefsEditor.putString(Constants.UID, uid)
                    prefsEditor.putString(Constants.LOGIN_USER, emailUser)
                    prefsEditor.putString(Constants.LOGIN_PASSWORD, passwordUser)
                    prefsEditor.apply()
                    startActivity(intent)
                    finish()
                }
                //is MainViewModel.AuthState.InvalidAuthentication -> {
                  //  binding.txtCredenciaisInvalidas.visibility = View.VISIBLE
               // }
                is MainViewModel.AuthState.Unauthenticated-> {
                    binding.txtCredenciaisInvalidas.visibility = View.VISIBLE
                    binding.loginProgressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun setButtonEffects(boolean: Boolean){
        val btnEntrar = binding.btEntrar
        if(boolean){
            btnEntrar.alpha = 1f
            btnEntrar.isClickable = true
            binding.icError.visibility = View.GONE
        }else{
            btnEntrar.alpha = 0.2f
            btnEntrar.isClickable = false
        }
    }
}