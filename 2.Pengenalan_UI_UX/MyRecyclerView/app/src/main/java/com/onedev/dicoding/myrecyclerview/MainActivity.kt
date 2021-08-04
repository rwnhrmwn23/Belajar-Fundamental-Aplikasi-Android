package com.onedev.dicoding.myrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.onedev.dicoding.myrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<Hero>()
    private var title = "Mode List"
    private var mode: Int = 0

    companion object {
        private const val STATE_TITLE = "state_string"
        private const val STATE_LIST = "state_list"
        private const val STATE_MODE = "state_mode"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvHeroes.setHasFixedSize(true)

        if (savedInstanceState == null) {
            setActionBarTitle(title)
            list.addAll(getListHeroes())
            showRecyclerList()
            mode = R.id.action_list
        } else {
            title = savedInstanceState.getString(STATE_TITLE).toString()
            val stateList = savedInstanceState.getParcelableArrayList<Hero>(STATE_LIST)
            val stateMode = savedInstanceState.getInt(STATE_MODE)

            setActionBarTitle(title)
            if (stateList != null) {
                list.addAll(stateList)
            }
            setMode(stateMode)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_TITLE, title)
        outState.putParcelableArrayList(STATE_LIST, list)
        outState.putInt(STATE_MODE, mode)
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun getListHeroes(): ArrayList<Hero> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.getStringArray(R.array.data_photo)

        val listHero = ArrayList<Hero>()
        for (position in dataName.indices) {
            val hero = Hero(dataName[position], dataDescription[position], dataPhoto[position])
            listHero.add(hero)
        }
        return listHero
    }

    private fun showRecyclerList() {
        val listHeroAdapter = ListHeroAdapter(list)
        binding.rvHeroes.layoutManager = LinearLayoutManager(this)
        binding.rvHeroes.adapter = listHeroAdapter
    }

    private fun showRecyclerGrid() {
        val gridHeroAdapter = GridHeroAdapter(list)
        binding.rvHeroes.layoutManager = GridLayoutManager(this, 2)
        binding.rvHeroes.adapter = gridHeroAdapter
    }

    private fun showRecyclerCardView() {
        val cardViewAdapter = CardViewHeroAdapter(list)
        binding.rvHeroes.layoutManager = LinearLayoutManager(this)
        binding.rvHeroes.adapter = cardViewAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.action_list -> {
                title = "Mode List"
                showRecyclerList()
            }
            R.id.action_grid -> {
                title = "Mode Grid"
                showRecyclerGrid()
            }
            R.id.action_card_view -> {
                title = "Mode CardView"
                showRecyclerCardView()
            }
        }
        mode = selectedMode
        setActionBarTitle(title)
    }
}