package com.example.talentmarketplace.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.talentmarketplace.R
import com.example.talentmarketplace.main.MainApp

class JobListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_list)

        app = application as MainApp
    }
}