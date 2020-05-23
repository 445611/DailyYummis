package cz.muni.fi.pv239.dailyyummies.service.networking.data

import com.google.gson.annotations.SerializedName

class DailyMenuResult(@SerializedName("daily_menus") val dailyMenus: List<DailyMenuHolder> = emptyList())

class DailyMenuHolder(@SerializedName("daily_menu") val dailyMenu: DailyMenu = DailyMenu())

class DailyMenu(val dishes: List<DishHolder> = emptyList())

class DishHolder(val dish: Dish = Dish())

class Dish(val name: String = "", val price: String = "")