package com.guilhermepalma.companysegment.presenter.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.guilhermepalma.companysegment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListSegments()
    }

    private fun setupListSegments() {
        TODO("Not yet implemented")
    }
}