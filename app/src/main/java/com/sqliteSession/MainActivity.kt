package com.sqliteSession

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.sqliteSession.databinding.ActivityMainBinding
import com.sqllitedemo.DataBaseHelper
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDataBase()
        initViews()
    }

    private fun initDataBase() {
        databaseHelper = DataBaseHelper(this)
        //databaseHelper.insertData("Ashok", 200)
    }

    private fun initViews() {
        binding.btnFetch.setOnClickListener() {
            val data = databaseHelper.readData()
            if (data != null) {
                binding.listOfUsers.adapter =
                    ArrayAdapter(this, R.layout.simple_list_item_1, data)
            }
            else{
                Toast.makeText(this,"No data Found",Toast.LENGTH_LONG).show()
            }

        }
        binding.btnSave.setOnClickListener() {
            Toast.makeText(this,"DATA SAVING",Toast.LENGTH_LONG).show()

            databaseHelper.insertData("Ashok", Random.nextInt())
        }
        binding.btnDelete.setOnClickListener() {
            databaseHelper.delete(3)
        }
        binding.btnUpdate.setOnClickListener {
            databaseHelper.update(4,"Hari",30)
        }
    }
}
