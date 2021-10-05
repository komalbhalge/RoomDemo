package com.kb.choco

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.kb.choco.ui.session.SessionManagerUtil
import com.kb.choco.util.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var navGraph: NavGraph
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        // Setup the toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Configure the navigation
        val navHost = nav_host_fragment as NavHostFragment
        navGraph = navHost.navController
            .navInflater.inflate(R.navigation.nav_graph)

        navController = findNavController(R.id.nav_host_fragment)

        changeStartDestination()

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.loginFragment, R.id.homeFragment)
        )

        navController.graph = navGraph

        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miOrders -> {
                navController.navigate(R.id.action_homeFragment_to_orderListFragment)
                true
            }
            R.id.miLogout -> {
                SessionManagerUtil.endUserSession(this)
                changeStartDestination()
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.nav_graph, true)
                    .setLaunchSingleTop(true)
                    .build()
                navController.navigate(R.id.loginFragment, null, navOptions)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    fun changeStartDestination() {

        val destination = if (SessionManagerUtil.isSessionValid(this)) {
            R.id.homeFragment
        } else {
            R.id.loginFragment
        }
        navGraph.startDestination = destination
    }
}