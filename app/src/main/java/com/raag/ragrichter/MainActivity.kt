package com.raag.ragrichter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.raag.ragrichter.adapter.MainAdapter
import com.raag.ragrichter.databinding.ActivityMainBinding
import com.raag.ragrichter.data.Terremoto
import com.raag.ragrichter.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel:MainViewModel
    private val adapter = MainAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.tList.observe(this, {
            binding.recyclerView.adapter = adapter
            adapter.submitList(it)
            viewEmptyList(it)
        })

        adapter.onItemClickListener = {
            Toast.makeText(this, it.place, Toast.LENGTH_SHORT).show()
        }

    }

    private fun viewEmptyList(it: MutableList<Terremoto>) {
        if (it.isEmpty()) {
            binding.pbEmpty.visibility = View.VISIBLE
        } else {
            binding.pbEmpty.visibility = View.GONE
        }
    }
}