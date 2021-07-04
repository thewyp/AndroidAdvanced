package com.thewyp.androidadvanced

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thewyp.androidadvanced.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scrollerView.setOnClickListener {
//            it.scrollBy(-1 * 10, -1 * 10)
        }

    }
}