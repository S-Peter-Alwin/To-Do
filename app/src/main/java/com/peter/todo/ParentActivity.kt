package com.peter.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.peter.todo.databinding.ActivityParentBinding

class ParentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityParentBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

//        loadListFragment()
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

//    private fun loadListFragment() {
//        val listFragment : ToDoListFragment = ToDoListFragment()
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.container, listFragment)
//            .commit()
//
//    }
}