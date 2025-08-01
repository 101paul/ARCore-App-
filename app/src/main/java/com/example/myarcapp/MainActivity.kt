package com.example.myarcapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myarcapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val drillList = assets.list("")?.filter{it.endsWith(".glb")}?:listOf()
        val adapter = ArrayAdapter(this,R.layout.spinner_item_view,drillList)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.startbtn.setOnClickListener{
            val selectedDrill = binding.spinner.selectedItem.toString()
            Toast.makeText(this,"Starting $selectedDrill....",Toast.LENGTH_LONG).show()
            val intent = Intent(this, ArDrill::class.java)
            intent.putExtra("data", selectedDrill)
            startActivity(intent)
        }

    }
}