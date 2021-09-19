package br.com.marco.desafioioasys.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.marco.desafioioasys.data.model.Enterprise
import br.com.marco.desafioioasys.databinding.ItemEmpresaBinding
import br.com.marco.desafioioasys.ui.DetalheEmpresaActivity
import br.com.marco.desafioioasys.util.Constants
import com.bumptech.glide.Glide

class EnterpriseListAdapter(var mContext: Context
) : ListAdapter<Enterprise, EnterpriseListAdapter.ViewHolder> (DiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnterpriseListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEmpresaBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EnterpriseListAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {

        }
    }

    inner class ViewHolder(
        private val binding: ItemEmpresaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Enterprise) {
            val name = item.enterprise_name
            val id = item.id
            binding.tvEmpresa.text = name
            binding.tvNegocio.text = item.enterprise_type.enterprise_type_name
            binding.tvPais.text = item.country
            Glide.with(binding.root.context)
                .load(Constants.BASE_URL + item.photo).into(binding.imgEmpresa)

            binding.mcvContent.setOnClickListener {
                val intent = Intent(mContext, DetalheEmpresaActivity::class.java)
                intent.putExtra(Constants.ENTERPRISE_ID, id)
                mContext.startActivity(intent)
            }
        }
    }
}




class DiffCallback : DiffUtil.ItemCallback<Enterprise>() {
    override fun areItemsTheSame(oldItem: Enterprise, newItem: Enterprise) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Enterprise, newItem: Enterprise) = oldItem.id == newItem.id
}