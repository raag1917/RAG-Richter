package com.raag.ragrichter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.raag.ragrichter.adapter.MainAdapter
import com.raag.ragrichter.databinding.ActivityMainBinding
import com.raag.ragrichter.model.Terremoto

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val terremotos = mutableListOf<Terremoto>()
        terremotos.add(Terremoto("1","Comuna 13, Medellín, Antioquia. Colombia",4.5, 23423423523, -122.3453, 32.92394))
        terremotos.add(Terremoto("2","Comuna 14, Medellín, Antioquia. Colombia",3.5, 23423423523, -122.3453, 32.92394))
        terremotos.add(Terremoto("3","Comuna 12, Medellín, Antioquia. Colombia",2.5, 23423423523, -122.3453, 32.92394))

        val adapter = MainAdapter()

        adapter.onItemClickListener = {
            Toast.makeText(this, it.place, Toast.LENGTH_SHORT).show()
        }

        binding.recyclerView.adapter = adapter
        adapter.submitList(terremotos)

        if(terremotos.isEmpty()){
            binding.pbEmpty.visibility = View.VISIBLE
        } else{
            binding.pbEmpty.visibility = View.GONE
        }
    }
}