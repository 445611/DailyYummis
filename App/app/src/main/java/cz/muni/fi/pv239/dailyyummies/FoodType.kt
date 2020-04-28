package cz.muni.fi.pv239.dailyyummies

enum class FoodType(val foodName: String) {
    HAMBURGER("Hamburger"),
    PIZZA("Pizza"),
    ITALIAN("Italian"),
    CHINA("China"),
    INDIAN("Indian"),
    FAST_FOOD("Fast food"),
    KEBAB("Kebab"),
    CZECH_KITCHEN("Czech kitchen");

    companion object {
        fun allFoodTypes(): List<FoodType> {
            return values().toList()
        }
    }
}