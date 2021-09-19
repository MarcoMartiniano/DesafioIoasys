package br.com.marco.desafioioasys.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.marco.desafioioasys.databinding.ActivityDetalheEmpresaBinding
import br.com.marco.desafioioasys.presentation.DetalheEmpresaViewModel
import br.com.marco.desafioioasys.util.Constants
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetalheEmpresaActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetalheEmpresaBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<DetalheEmpresaViewModel>()


    private var token = ""
    private var client = ""
    private var uid = ""
    private var id = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getInfoFromExtras()
        observers()

        viewModel.getEnterprise(token, client, uid)

        binding.toolbarDetalhes.setNavigationOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }

    private fun getInfoFromExtras(){
        val preference=getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        token = preference.getString(Constants.ACCESS_TOKEN, "").toString()
        client = preference.getString(Constants.CLIENT, "").toString()
        uid = preference.getString(Constants.UID, "").toString()
        id = intent.getIntExtra(Constants.ENTERPRISE_ID,0)
    }

    private fun observers(){
        viewModel.enterpriseState.observe(this){
            when (it) {
                DetalheEmpresaViewModel.State.Loading -> binding.detailsProgressBar.visibility = View.VISIBLE
                is DetalheEmpresaViewModel.State.Error -> {
                    binding.detailsProgressBar.visibility= View.GONE
                    val msg = "Houve um erro: $it.error.message"
                    binding.tvDetalheEmpresa.text = msg
                }
                is DetalheEmpresaViewModel.State.Success -> {
                    binding.detailsProgressBar.visibility = View.GONE
                    setInformation(it)
                }
            }
        }
    }
    private fun setInformation(state: DetalheEmpresaViewModel.State.Success) {
        val enterprise = state.enterprise.enterprises.filter { it.id == id }
        binding.tvDetalheEmpresa.text = enterprise[0].description
       Glide.with(binding.imgEmpresa)
            .load(Constants.BASE_URL + enterprise[0].photo).into(binding.imgEmpresa)
    }

}