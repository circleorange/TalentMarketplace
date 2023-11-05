package com.example.talentmarketplace.activities

import android.app.DatePickerDialog
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
import com.example.talentmarketplace.models.Location
import com.example.talentmarketplace.models.MarketplaceModel

import com.google.android.material.snackbar.Snackbar
import timber.log.Timber.i
import java.util.Calendar

class MarketplaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobBinding
    var job = MarketplaceModel()
    lateinit var app: MainApp
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        i("onCreate() - Job Activity started")

        edit = false

        binding = ActivityJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        registerMapCallback()
        setupSalaryRangeSliderListener()
        setupAddButtonListener()
        setupSetLocationButtonListener()
        setupStartDatePickerListener()

        if (intent.hasExtra("job_edit")) {
            edit = true
            job = intent.extras?.getParcelable("job_edit")!!
            binding.jobTitle.setText(job.title)
            binding.description.setText(job.description)
            binding.companyName.setText(job.companyName)
            job.startDate.let {
                val calendar = Calendar.getInstance().apply { time = it }
                binding.startDate.updateDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)) }
            job.minSalary.let { binding.salaryRange.values = listOf(it, job.maxSalary ?: it) }
            binding.btnAdd.setText(R.string.save_job) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_job, menu)
        if (edit) menu?.getItem(1)?.isVisible = true
        return super.onCreateOptionsMenu(menu) }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
            R.id.item_delete -> {
                app.jobs.delete(job)
                setResult(99)
                finish() } }
        return super.onOptionsItemSelected(item) }

    private fun registerMapCallback() {
        mapIntentLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            result -> when (result.resultCode) {
                RESULT_OK -> { if (result.data != null) {
                    val location = result.data!!.extras?.getParcelable<Location>("location")!!
                    job.lat = location.lat
                    job.lng = location.lng
                    job.zoom = location.zoom
                    i("registerMapCallback() - valid location: $location") } }
                RESULT_CANCELED -> { } else -> { } } }
    }

    private fun showStartDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            binding.startDate.updateDate(selectedYear, selectedMonth, selectedDay)
        }, year, month, day)
        dpd.show()
    }

    private fun setupSalaryRangeSliderListener() {
        binding.salaryRange.addOnChangeListener { slider, _, _ ->
            val values = slider.values
            job.minSalary = values[0]
            job.maxSalary = values[1]
        }
    }

    private fun setupAddButtonListener() {
        binding.btnAdd.setOnClickListener() {
            i("onCreate() - add button pressed")

            job.title  = binding.jobTitle.text.toString()
            job.description = binding.description.text.toString()
            job.companyName = binding.companyName.text.toString()
            val calendar = Calendar.getInstance().apply {
                set(
                    binding.startDate.year,
                    binding.startDate.month,
                    binding.startDate.dayOfMonth) }
            job.startDate = calendar.time
            job.minSalary = binding.salaryRange.values[0]
            job.maxSalary = binding.salaryRange.values[1]

            if (job.title.isEmpty()) {
                i("onCreate() - add job button pressed - invalid input")
                Snackbar
                    .make(it, R.string.invalid_job_title, Snackbar.LENGTH_LONG)
                    .show()
            }
            else {
                i("onCreate() - add job button pressed - valid input: $job")
                if (edit) { app.jobs.update(job.copy()) }
                else { app.jobs.create(job.copy()) }
                setResult(RESULT_OK)
                finish() } } }

    private fun setupSetLocationButtonListener() {
        binding.jobLocation.setOnClickListener {
            i("onCreate() - set location button pressed")

            val location = Location(53.2744, -9.0494, 15f)
            if (job.zoom != 0f) {
                location.lat = job.lat
                location.lng = job.lng
                location.zoom = job.zoom }

            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)

            mapIntentLauncher.launch(launcherIntent) } }

    private fun setupStartDatePickerListener() {
        binding.startDate.setOnClickListener { showStartDatePickerDialog() }
    }
}
