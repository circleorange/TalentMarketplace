package com.example.talentmarketplace.models

import android.content.Context
import android.net.Uri
import com.example.talentmarketplace.helpers.exists
import com.example.talentmarketplace.helpers.*
import timber.log.Timber
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import timber.log.Timber.i
import java.lang.reflect.Type
import java.util.Random

const val JSON_FILE = "jobs.json"

val gsonBuilder: Gson = GsonBuilder()
    .setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()

val listType: Type = object: TypeToken<ArrayList<MarketplaceModel>>() {}.type

fun generateRandomID(): Long { return Random().nextLong() }

class JobJSONStore(private val context: Context): JobStore {
    var jobs = mutableListOf<MarketplaceModel>()

    init { if (exists(context, JSON_FILE)) { deserialize() } }

    override fun findAll(): List<MarketplaceModel> {
        logAll()
        return jobs }

    override fun create(job: MarketplaceModel) {
        job.id = generateRandomID()
        jobs.add(job)
        serialize() }

    override fun update(job: MarketplaceModel) {
        val jobsList = findAll() as ArrayList<MarketplaceModel>
        var foundJob: MarketplaceModel? = jobsList.find { updatedJob -> updatedJob.id == job.id }
        if (foundJob == null) { i("update() - null job found"); return }

        foundJob.title = job.title
        foundJob.description = job.description
        foundJob.lat = job.lat
        foundJob.lng = job.lng
        foundJob.zoom = job.zoom
        serialize() }

    override fun delete(job: MarketplaceModel) {
        jobs.remove(job)
        serialize() }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(jobs, listType)
        write(context, JSON_FILE, jsonString) }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        jobs = gsonBuilder.fromJson(jsonString, listType) }

    private fun logAll() { jobs.forEach { Timber.i("$it") } }

}

class UriParser: JsonDeserializer<Uri>, JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri { return Uri.parse(json?.asString) }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement { return JsonPrimitive(src.toString()) }
}