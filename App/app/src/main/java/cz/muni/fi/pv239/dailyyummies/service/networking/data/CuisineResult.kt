package cz.muni.fi.pv239.dailyyummies.service.networking.data

import com.google.gson.annotations.SerializedName

class CuisineResult(val cuisines: List<CuisineHolder> = emptyList())

class CuisineHolder(val cuisine: Cuisine = Cuisine())

class Cuisine(@SerializedName("cuisine_id") val id: Int = -1, @SerializedName("cuisine_name") val name: String = "")