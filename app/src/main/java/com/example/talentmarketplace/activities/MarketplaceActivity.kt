package com.example.talentmarketplace.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.talentmarketplace.databinding.ActivityJobBinding
import com.example.talentmarketplace.main.MainApp
import com.example.talentmarketplace.models.MarketplaceModel

import com.google.android.material.snackbar.Snackbar
import timber.log.Timber.i

class MarketplaceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJobBinding

    var job = MarketplaceModel()

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        i("onCreate() - Marketplace Activity started")

        app = application as MainApp

        binding.btnAdd.setOnClickListener() {
            i("onCreate() - add button pressed")

            job.title  = binding.jobTitle.text.toString()
            job.description = binding.description.text.toString()

            if (job.title.isEmpty()) {
                i("onCreate() - add button pressed - invalid")

                Snackbar
                    .make(it, "Job title cannot be empty", Snackbar.LENGTH_LONG)
                    .show() }
            else {
                i("onCreate() - add button pressed - valid")

                app.jobs.add(job.copy())
                for (i in app.jobs.indices) {
                    i("Job[$i]: ${this.app.jobs[i]}") } }
        }
    }
}