package gh.code.dictionary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.elevation.SurfaceColors
import gh.code.dictionary.core.ActivityRequired
import gh.code.dictionary.core.CommonUi

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var activityRequired: ActivityRequired

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHost.navController

        findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setupWithNavController(navController)

        activityRequired = (DependencyProvider.commonUi as ActivityRequired)
        activityRequired.onCreated(this)

    }

    override fun onStart() {
        super.onStart()
        activityRequired.onStarted()
    }

    override fun onStop() {
        super.onStop()
        activityRequired.onStopped()
    }

    override fun onDestroy() {
        super.onDestroy()
        activityRequired.onDestroyed()
    }

    override fun onNavigateUp(): Boolean {
        return super.onNavigateUp() || navController.navigateUp()
    }
}