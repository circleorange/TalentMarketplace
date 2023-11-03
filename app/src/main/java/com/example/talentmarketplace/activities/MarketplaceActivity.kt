package com.example.talentmarketplace.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.talentmarketplace.R
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

        i("onCreate() - Job Activity started")

        binding = ActivityJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        if (intent.hasExtra("job_edit")) {
            job = intent.extras?.getParcelable("job_edit")!!
            binding.jobTitle.setText(job.title)
            binding.description.setText(job.description) }

        binding.btnAdd.setOnClickListener() {
            i("onCreate() - add button pressed")

            job.title  = binding.jobTitle.text.toString()
            job.description = binding.description.text.toString()

            if (job.title.isEmpty()) {
                i("onCreate() - add button pressed - invalid input")
                Snackbar
                    .make(it, "Job title cannot be empty", Snackbar.LENGTH_LONG)
                    .show() }
            else {
                i("onCreate() - add button pressed - valid input: $job")
                app.jobs.create(job.copy())
                setResult(RESULT_OK)
                finish() } } }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_job, menu)
        return super.onCreateOptionsMenu(menu) }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) { R.id.item_cancel -> { finish() } }
        return super.onOptionsItemSelected(item) }
}