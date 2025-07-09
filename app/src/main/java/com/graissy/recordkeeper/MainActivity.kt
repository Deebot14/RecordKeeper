package com.graissy.recordkeeper

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import com.graissy.recordkeeper.cycling.CyclingFragment
import com.graissy.recordkeeper.databinding.ActivityMainBinding
import com.graissy.recordkeeper.running.RunningFragment


class MainActivity : AppCompatActivity(), OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // We bind an Activity like this ↓↓
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // We make our BottomNavigationView working ↓↓
        binding.bottomNav.setOnItemSelectedListener(this)
    }

    // We make our menu first (Menu Resource File)
    // Then we make this function (onCreateOptionsMenu) ↓↓
    // We inflate our menu.xml in the given menu in the parameter
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){
            R.id.reset_running -> {
                resetRunningRecords()
                Toast.makeText(this, "Running Records cleared", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.reset_cycling -> {
                resetCyclingRecords()
                Toast.makeText(this, "Cycling Records cleared", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.reset_all -> {
                resetAllRecords()
                Toast.makeText(this, "All records cleared", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun resetRunningRecords() {
        val runningPreferences = getSharedPreferences("running", Context.MODE_PRIVATE)
        runningPreferences.edit {
            clear() // Clears all records for running
        }
    }

    private fun resetCyclingRecords() {
        val cyclingPreferences = getSharedPreferences("cycling", Context.MODE_PRIVATE)
        cyclingPreferences.edit {
            clear() // Clears all records for cycling
        }

    }

    private fun resetAllRecords() {
        resetRunningRecords()
        resetCyclingRecords()
    }


    private fun onRunningClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, RunningFragment( ))
        }
        return true // return true to select the running icon
    }

    private fun onCyclingClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, CyclingFragment( ))
        }
        return true // return true to select the cycling icon
    }

    // This function returns a boolean
    override fun onNavigationItemSelected(item: MenuItem) = when (item.itemId) {
            R.id.nav_cycling -> onCyclingClicked() // returns true (inline)
            R.id.nav_running -> onRunningClicked() // returns true (inline)
            else -> false // return false when another icon is selected that we're not expecting
        }
    }


