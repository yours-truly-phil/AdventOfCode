package aoc2020

import java.io.File

fun main() {
    val day21 = Day21(File("files/2020/day21.txt").readLines())
    println(day21.part1())
    println(day21.part2())
}

class Day21(lines: List<String>) {

    private val allergens = HashMap<String, Allergen>()
    private val ingredients = HashMap<String, HashSet<Food>>()
    private val foods = ArrayList<Food>()

    init {
        lines.forEach { line ->
            val allergensInLine = line.substring(line.indexOf("(") + 10, line.indexOf(")"))
                .split(", ")
                .map { allergens.computeIfAbsent(it) { Allergen(it) } }
                .map { it.name to it }.toMap()
            val food = Food(
                line.substring(0, line.indexOf("(") - 1)
                    .split(" "), allergensInLine
            )
            foods.add(food)
            food.ingredients.forEach {
                ingredients.computeIfAbsent(it) { HashSet() }
                ingredients[it]!!.add(food)
            }
            allergensInLine.forEach { it.value.inFoods.add(food) }
        }
    }

    fun part1(): Int {
        for (allergen in allergens.values) {
            val foodsWithAllergen = allFoodsWithAllergen(allergen)
            val possibleIngredientsForAllergen = allIngredientsCommonInFoods(foodsWithAllergen)
            allergen.possibleIngredients.addAll(possibleIngredientsForAllergen)
        }

        while (allergensWithUnknownIngredients().isNotEmpty()) {
            val allergenWithOneIngredient = allergenWithOnePossibleIngredient()
            for (allergen in allergenWithOneIngredient) {
                allergen.ingredient = allergen.possibleIngredients.elementAt(0)
                allergen.isFound = true
                for (a in allergens.values) {
                    a.possibleIngredients.remove(allergen.ingredient)
                }
            }
        }

        return countUnAssignedIngredients()
    }

    fun part2(): String {
        part1()
        return getDangerousIngredientList()
    }

    private fun getDangerousIngredientList(): String {
        return allergens.values
            .sortedBy { it.name }
            .map { it.ingredient }
            .joinToString(",")
    }

    private fun countUnAssignedIngredients(): Int {
        val allIngredientsCount = allIngredientsAndCount()
        for (ingredient in allAssignedIngredients()) {
            allIngredientsCount.remove(ingredient)
        }
        val result = allIngredientsCount.values.sum()
        return result
    }

    private fun allIngredientsAndCount(): HashMap<String, Int> {
        val result = HashMap<String, Int>()
        for (food in foods) {
            for (ingredient in food.ingredients) {
                result.putIfAbsent(ingredient, 0)
                result[ingredient] = result[ingredient]!! + 1
            }
        }
        return result
    }

    private fun allAssignedIngredients(): List<String> {
        return allergens.values.map { it.ingredient }
    }

    private fun allergenWithOnePossibleIngredient(): Set<Allergen> {
        return allergens.filter { it.value.possibleIngredients.size == 1 }.values.toSet()
    }

    private fun allergensWithUnknownIngredients(): Set<Allergen> {
        return allergens.filter { !it.value.isFound }.values.toSet()
    }

    private fun allFoodsWithAllergen(allergen: Allergen): Set<Food> {
        return foods.filter { it.allergens.containsKey(allergen.name) }.toSet()
    }

    private fun allIngredientsCommonInFoods(foods: Set<Food>): Set<String> {
        val allIngredientsInThoseFoods = HashSet<String>()
        foods.forEach { it.ingredients.forEach { ingredient -> allIngredientsInThoseFoods.add(ingredient) } }
        return allIngredientsInThoseFoods.filter {
            foods.all { food -> food.ingredients.contains(it) }
        }.toSet()
    }

    data class Food(val ingredients: List<String>, val allergens: Map<String, Allergen>)

    data class Allergen(val name: String) {
        val inFoods = HashSet<Food>()
        val possibleIngredients = HashSet<String>()
        var ingredient = ""
        var isFound = false
    }
}