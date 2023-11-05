package com.example.talentmarketplace.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        i("onCreate() - Job Activity started")

        var edit = false

        binding = ActivityJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        if (intent.hasExtra("job_edit")) {
            edit = true
            job = intent.extras?.getParcelable("job_edit")!!
            binding.jobTitle.setText(job.title)
            binding.description.setText(job.description)
            binding.btnAdd.setText(R.string.save_job) }

        // Button - Create / Update Job
        binding.btnAdd.setOnClickListener() {
            i("onCreate() - add button pressed")

            job.title  = binding.jobTitle.text.toString()
            job.description = binding.description.text.toString()

            if (job.title.isEmpty()) {
                i("onCreate() - add button pressed - invalid input")
                Snackbar
                    .make(it, R.string.invalid_job_title, Snackbar.LENGTH_LONG)
                    .show() }
            else {
                i("onCreate() - add button pressed - valid input: $job")
                if (edit) { app.jobs.update(job.copy()) }
                else { app.jobs.create(job.copy()) }
                setResult(RESULT_OK)
                finish() } }

        // Button - Set Location
        binding.jobLocation.setOnClickListener {
            i("Set Location Pressed") }

        registerMapCallback()

        binding.jobLocation.setOnClickListener {
            val launcherIntent = Intent(this, MapActivity::class.java)
            mapIntentLauncher.launch(launcherIntent) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_job, menu)
        return super.onCreateOptionsMenu(menu) }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) { R.id.item_cancel -> { finish() } }
        return super.onOptionsItemSelected(item) }

    private fun registerMapCallback() {
        mapIntentLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { i("Map loaded") } }
}