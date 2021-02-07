package com.raag.ragrichter.adapter

import android.content.Context
import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raag.ragrichter.R
import com.raag.ragrichter.databinding.ItemTerremotoBinding
import com.raag.ragrichter.data.Earthquake

//Permite tomar el nombre de la clase y asignarlo a la variable TAG
private val TAG = MainAdapter::class.java.simpleName

class MainAdapter(val context: Context): ListAdapter<Earthquake, MainAdapter.MainViewHolder>(DiffCallback) {

    companion object DiffCallback: DiffUtil.ItemCallback<Earthquake>(){
        override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem == newItem
        }

    }

    lateinit var onItemClickListener: (Earthquake) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainViewHolder(ItemTerremotoBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    inner class MainViewHolder(private val binding: ItemTerremotoBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(earthquake: Earthquake){


            binding.tvMagnitud.text =  context.getString(R.string.magnitude_format, earthquake.magnitude)
            binding.tvLugar.text = earthquake.place
            binding.btVer.setOnClickListener {
                if(::onItemClickListener.isInitialized){
                    onItemClickListener(earthquake)
                }else{
                    d(TAG, "onItemClickListener no Initialized" )
                }
            }
            //evita que el cargue de la información haga explotar la App
            binding.executePendingBindings()
        }

    }
}