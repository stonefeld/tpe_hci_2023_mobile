package ar.edu.itba.grupo10.vfit.utils

import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.data.models.Error

fun stringToRes(string: String): Int = when (string) {
    "rookie" -> R.string.rookie
    "beginner" -> R.string.beginner
    "intermediate" -> R.string.intermediate
    "advanced" -> R.string.advanced
    "expert" -> R.string.expert
    "male" -> R.string.male
    "female" -> R.string.female
    else -> R.string.not_found
}

fun resToString(resource: Int): String = when (resource) {
    R.string.rookie -> "rookie"
    R.string.beginner -> "beginner"
    R.string.intermediate -> "intermediate"
    R.string.advanced -> "advanced"
    R.string.expert -> "expert"
    R.string.male -> "male"
    R.string.female -> "female"
    else -> "not_found"
}

fun codeToMessage(error: Error): Int = when (error.code) {
    1 -> R.string.error_1
    4 -> R.string.error_4
    98 -> R.string.error_98
    else -> R.string.not_found
}