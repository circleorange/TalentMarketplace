package com.example.talentmarketplace.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talentmarketplace.R
import com.example.talentmarketplace.adapters.JobAdapter
import com.example.talentmarketplace.adapters.JobListener
import com.example.talentmarketplace.databinding.ActivityJobListBinding
import com.example.talentmarketplace.main.MainApp
import com.example.talentmarketplace.models.MarketplaceModel

class JobListActivity : AppCompatActivity(), JobListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityJobListBinding
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJobListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = JobAdapter(app.jobs.findAll(), this) }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu) }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, JobActivity::class.java)
                getResults.launch(launcherIntent) } }
        return super.onOptionsItemSelected(item) }

    private val getResults = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult() ) {
        if (it.resultCode == Activity.RESULT_OK) {
            (binding
                .recyclerView
                .adapter)?.notifyItemRangeChanged(0, app.jobs.findAll().size) } }

    override fun onJobClick(job: MarketplaceModel, listIndex: Int) {
        val launcherIntent = Intent(this, JobActivity::class.java)
        launcherIntent.putExtra("job_edit", job)
        position = listIndex
        getClickResult.launch(launcherIntent) }

    private val getClickResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult() ) {
        if (it.resultCode == Activity.RESULT_OK) {
            (binding
                .recyclerView
                .adapter)?.notifyItemRangeChanged(0, app.jobs.findAll().size) }
        // delete job operation
        else if (it.resultCode == 99 ) {
            binding.recyclerView.adapter = JobAdapter(app.jobs.findAll(), this) } }
}
