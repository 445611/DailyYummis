package cz.muni.fi.pv239.dailyyummies.service.networking.data

import com.google.gson.annotations.SerializedName

class CitiesResult(@SerializedName("location_suggestions") val cities: List<City> = emptyList(), val status: String = "")

class City(val id: Int = -1, val name: String = "")