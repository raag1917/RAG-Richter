package com.raag.ragrichter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.raag.ragrichter.adapter.MainAdapter
import com.raag.ragrichter.databinding.ActivityMainBinding
import com.raag.ragrichter.responses.Responses
import com.raag.ragrichter.viewmodel.MainViewModel
import com.raag.ragrichter.viewmodel.ViewModelFactory

@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
private const val KEY_SORT = "key"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel:MainViewModel
    private val adapter = MainAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sortType = getSortType()
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(application, sortType)
        ).get(MainViewModel::class.java)



        viewModel.eqList.observe(this, {
            binding.recyclerView.adapter = adapter
            adapter.submitList(it)
        })

        viewModel.status.observe(this, { earthquakes ->
            when (earthquakes) {
                Responses.LOADING -> {
                    binding.pbEmpty.visibility = View.VISIBLE
                }
                Responses.DONE -> {
                    binding.pbEmpty.visibility = View.GONE
                    Toast.makeText(this, getString(R.string.datos_cargados), Toast.LENGTH_SHORT).show()
                }
                Responses.ERROR -> {
                    binding.pbEmpty.visibility = View.GONE
                }
            }
        })

        adapter.onItemClickListener = {
            Toast.makeText(this, it.place, Toast.LENGTH_SHORT).show()
        }

    }

    private fun getSortType(): Boolean {
        val pref = getPreferences(MODE_PRIVATE)
        return pref.getBoolean(KEY_SORT, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.byMagnitude -> {
                viewModel.reloadEarthquakesFromDb(true)
                saveSortType(true)
            }
            R.id.byTime -> {
                viewModel.reloadEarthquakesFromDb(false)
                saveSortType(false)
            }
        }
        return super.onOptionsItemSelected(item)
    }



    private fun saveSortType(sortByMagnitude: Boolean){
        val pref = getPreferences(MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean(KEY_SORT, sortByMagnitude)
        editor.apply()
    }


}