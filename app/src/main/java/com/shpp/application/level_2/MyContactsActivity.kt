package com.shpp.application.level_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shpp.application.databinding.MyContactsActivityBinding

class MyContactsActivity: AppCompatActivity() {

    private val binding: MyContactsActivityBinding by lazy {
        MyContactsActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}