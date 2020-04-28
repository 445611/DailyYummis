package cz.muni.fi.pv239.dailyyummies.menu.restaurant

class Restaurant(val name: String, val rating: Float, var distance: Int, val menu: MutableSet<Meal>) {
}