package za.co.shepherd.weatherapp.ui.home

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import za.co.shepherd.weatherapp.R
import za.co.shepherd.weatherapp.core.BaseActivity
import za.co.shepherd.weatherapp.databinding.ActivityMainBinding
import za.co.shepherd.weatherapp.utils.extensions.checkDeviceRoot
import za.co.shepherd.weatherapp.utils.extensions.hide
import za.co.shepherd.weatherapp.utils.extensions.show

@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>(
    R.layout.activity_main,
    MainActivityViewModel::class.java
), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO check if device is rooted
  //      if(checkDeviceRoot())
   //         return

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setTransparentStatusBar()
        setupNavigation()
    }

    private fun setTransparentStatusBar() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItemSearch -> {
                findNavController(R.id.container_fragment).navigate(R.id.searchFragment)
                true
            }
            else -> false
        }
    }

    private fun setupNavigation() {
        val appBarConfig = AppBarConfiguration(
            setOf(R.id.dashboardFragment),
            binding.drawerLayout
        )

        val navController = findNavController(R.id.container_fragment)
        binding.toolbar.overflowIcon = getDrawable(R.drawable.ic_menu)
        binding.toolbar.navigationIcon?.setTint(Color.parseColor("#130e51"))
        setupWithNavController(binding.toolbar, navController, appBarConfig)
        setupWithNavController(binding.navigationView, navController)
        binding.navigationView.setNavigationItemSelectedListener(this)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> {
                    binding.toolbar.hide()
                }
                R.id.dashboardFragment -> {
                    binding.toolbar.show()
                    binding.toolbar.setNavigationIcon(R.drawable.ic_menu)
                }
                R.id.searchFragment -> {
                    binding.toolbar.hide()
                }
                else -> {
                    binding.toolbar.setNavigationIcon(R.drawable.ic_back)
                    binding.toolbar.show()
                }
            }
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            findNavController(R.id.container_fragment),
            binding.drawerLayout
        )
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        return item.onNavDestinationSelected(findNavController(R.id.container_fragment)) || super.onOptionsItemSelected(
            item
        )
    }
}