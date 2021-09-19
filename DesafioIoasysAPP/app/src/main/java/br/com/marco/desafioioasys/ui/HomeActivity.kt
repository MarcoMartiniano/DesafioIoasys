package br.com.marco.desafioioasys.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import br.com.marco.desafioioasys.R
import br.com.marco.desafioioasys.data.model.Enterprise
import br.com.marco.desafioioasys.databinding.ActivityHomeBinding
import br.com.marco.desafioioasys.presentation.HomeViewModel
import br.com.marco.desafioioasys.ui.adapters.EnterpriseListAdapter
import br.com.marco.desafioioasys.util.Constants
import br.com.marco.desafioioasys.util.hideSoftKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity(), SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    private lateinit var listEnterprises: List<Enterprise>
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<HomeViewModel>()
    private val adapter by lazy { EnterpriseListAdapter(this) }

    private var token = ""
    private var client = ""
    private var uid = ""
    private var controle = 0
    private var queryString = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        getHeadersFromExtras()
        binding.rvEnterprise.adapter = adapter
        listeners()
        observers()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnSearchClickListener { binding.imgLogo.visibility = View.GONE }
        searchView.setOnQueryTextListener(this)
        searchView.setOnCloseListener(this)
        searchView.queryHint = "Pesquisar"
        searchView.maxWidth = Integer.MAX_VALUE
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        controle = 2
        queryString = query ?: ""
        query?.let { viewModel.getEnterpriseList(token,client,uid) }
        binding.root.hideSoftKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }


    override fun onClose(): Boolean {
        binding.imgLogo.visibility = View.VISIBLE
        return false
    }

    //função para setar os observers
    private fun observers(){
        viewModel.listEnterpriseState.observe(this){
            when (it) {
                HomeViewModel.State.Loading -> binding.searchProgressBar.visibility = View.VISIBLE
                is HomeViewModel.State.Error -> {
                    binding.searchProgressBar.visibility = View.GONE
                    binding.txtClickBuscar.text = "Houve um erro: ${it.error.message}"
                }
                is HomeViewModel.State.Success -> {
                    binding.searchProgressBar.visibility = View.GONE
                    binding.rvEnterprise.visibility = View.VISIBLE
                    listEnterprises = it.list.enterprises
                    when(controle){
                        1 -> {
                            adapter.submitList(listEnterprises)
                            adapter.notifyDataSetChanged()
                            binding.txtClickBuscar.visibility = View.GONE
                        }
                        2 -> {
                            queryName(queryString)
                        }
                    }

                }
            }
        }
    }

    //função para pegar Headers
    private fun getHeadersFromExtras(){
        val preference=getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        token = preference.getString(Constants.ACCESS_TOKEN, "").toString()
        client = preference.getString(Constants.CLIENT, "").toString()
        uid = preference.getString(Constants.UID, "").toString()
    }

    //função para setar listeners
    private fun listeners(){
        binding.btMostrarTodos.setOnClickListener {
            viewModel.getEnterpriseList(token,client,uid)
            controle = 1
        }
    }

    //função para filtrar o nome das empresas pela String digitada
    private fun queryName (query: String){
        val list = listEnterprises.filter {
            it.enterprise_name.contains(query)
        }

        if(list.isEmpty()){
            binding.txtClickBuscar.visibility = View.VISIBLE
            binding.txtClickBuscar.text = "Nenhuma empresa foi encontrada para buscar realizada."
        }else{
            binding.txtClickBuscar.visibility = View.GONE
        }
        adapter.submitList(list)
        adapter.notifyDataSetChanged()
    }
}