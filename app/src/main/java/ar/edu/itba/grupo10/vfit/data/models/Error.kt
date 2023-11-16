package ar.edu.itba.grupo10.vfit.data.models

data class Error(
    var code: Int?,
    var message: String,
    var description: List<String>? = null
)
