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
import com.example.talentmarketplace.databinding.ActivityJobListBinding
import com.example.talentmarketplace.main.MainApp

class JobListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityJobListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJobListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = JobAdapter(app.jobs) }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu) }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, MarketplaceActivity::class.java)
                getResults.launch(launcherIntent) } }
        return super.onOptionsItemSelected(item) }

    private val getResults = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult() ) {
        if (it.resultCode == Activity.RESULT_OK) {
            (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.jobs.size) } }
}