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
import com.raag.ragrichter.data.Terremoto

//Permite tomar el nombre de la clase y asignarlo a la variable TAG
private val TAG = MainAdapter::class.java.simpleName

class MainAdapter(val context: Context): ListAdapter<Terremoto, MainAdapter.MainViewHolder>(DiffCallback) {

    companion object DiffCallback: DiffUtil.ItemCallback<Terremoto>(){
        override fun areItemsTheSame(oldItem: Terremoto, newItem: Terremoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Terremoto, newItem: Terremoto): Boolean {
            return oldItem == newItem
        }

    }

    lateinit var onItemClickListener: (Terremoto) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainViewHolder(ItemTerremotoBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    inner class MainViewHolder(private val binding: ItemTerremotoBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(terremoto: Terremoto){


            binding.tvMagnitud.text =  context.getString(R.string.magnitude_format, terremoto.magnitude)
            binding.tvLugar.text = terremoto.place
            binding.btVer.setOnClickListener {
                if(::onItemClickListener.isInitialized){
                    onItemClickListener(terremoto)
                }else{
                    d(TAG, "onItemClickListener no Initialized" )
                }
            }
            //evita que el cargue de la información haga explotar la App
            binding.executePendingBindings()
        }

    }
}