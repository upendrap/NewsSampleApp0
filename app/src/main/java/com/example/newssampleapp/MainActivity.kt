package com.example.newssampleapp

import android.os.Bundle
import android.widget.LinearLayout.VERTICAL
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.newssampleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityMainBinding.inflate(layoutInflater).let {
            setContentView(it.root)
            val adapter = NewsAdapter(NewsItemDiffCallback())
            it.rvNewsItems.adapter = adapter
            it.rvNewsItems.addItemDecoration(DividerItemDecoration(this, VERTICAL))
        }
    }
}