package com.farrelfeno.submissionakhirbangkit.ui


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.farrelfeno.submissionakhirbangkit.R
import com.farrelfeno.submissionakhirbangkit.databinding.ActivityMainBinding
import com.farrelfeno.submissionakhirbangkit.response.ItemsItem
import com.farrelfeno.submissionakhirbangkit.theme.SettingPreferences
import com.farrelfeno.submissionakhirbangkit.theme.ThemeSetting
import com.farrelfeno.submissionakhirbangkit.theme.ThemeViewModel
import com.farrelfeno.submissionakhirbangkit.theme.ThemeViewModelFactory
import com.farrelfeno.submissionakhirbangkit.theme.dataStore
import com.farrelfeno.submissionakhirbangkit.ui.DetailUsersActivity.Companion.EXTRA_USERSNAME


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdapter
    private lateinit var mainviewmodel: MainViewModel
    private lateinit var themeViewModel: ThemeViewModel

    companion object {
        private val INIT_QUERY = "farrelf"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mainviewmodel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        initadapter()
        setUpSearchBar()
        mainviewmodel.setSearchUser(INIT_QUERY)

        mainviewmodel.listUser.observe(this) { items ->
            adapter.submitList(items)
        }
        mainviewmodel.isonLoading.observe(this) {
            showLoading(it)
        }

        themeViewModel = ViewModelProvider(
            this,
            ThemeViewModelFactory(SettingPreferences.getInstance(application.dataStore))
        ).get(ThemeViewModel::class.java)
        applyTheme()

        binding.btnFavorite.setOnClickListener{
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initadapter() {
        adapter = MainAdapter()

        val layoutManager = LinearLayoutManager(this)
        binding.rvuser.layoutManager = layoutManager
        adapter = MainAdapter()
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvuser.addItemDecoration(itemDecoration)
        binding.rvuser.adapter = adapter
    }



    private fun setUpSearchBar() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { text, actionId, event ->
                searchBar.text = searchView.text
                searchView.hide()
                mainviewmodel.setSearchUser(searchView.text.toString())
                false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.theme_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_night_mode -> {
                Intent(this, ThemeSetting::class.java).also {
                    val intent = Intent(this, ThemeSetting::class.java)
                    startActivity(intent)
                    return true
                }
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        applyTheme()
    }

    private fun applyTheme() {
        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}

