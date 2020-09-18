package com.ffzs.draw.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ffzs.draw.R
import com.ffzs.draw.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        actionBar?.setTitle(R.string.app_name)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clear.setOnClickListener(this)

        val metrics = resources.displayMetrics
        paintView.init(metrics)
        loading_progress?.visibility = View.GONE
    }

    override fun onClick(v: View?) {
        if (v!!.id == clear.id) {
            paintView!!.clear()
        }
    }
}