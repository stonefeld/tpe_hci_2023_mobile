package ar.edu.itba.grupo10.vfit.utils

import ar.edu.itba.grupo10.vfit.R

fun stringToRes(string: String): Int {
    val rookie = "rookie"
    val beginner = "beginner"
    val intermediate = "intermediate"
    val advanced = "advanced"
    val expert = "expert"
    val male = "male"
    val female = "female"

    val aux: Int = when (string) {
        rookie -> R.string.rookie
        beginner -> R.string.beginner
        intermediate -> R.string.intermediate
        advanced -> R.string.advanced
        expert -> R.string.expert
        male -> R.string.male
        female -> R.string.female
        else -> {
            R.string.not_found
        }
    }
    return aux
}

fun resToString(resource: Int): String {
    val rookie = R.string.rookie
    val beginner = R.string.beginner
    val intermediate = R.string.intermediate
    val advanced = R.string.advanced
    val expert = R.string.expert
    val male = R.string.male
    val female = R.string.female

    val aux: String = when (resource) {
        rookie -> "rookie"
        beginner -> "beginner"
        intermediate -> "intermediate"
        advanced -> "advanced"
        expert -> "expert"
        male -> "male"
        female -> "female"
        else -> {
            "not_found"
        }
    }
    return aux
}