package ar.edu.itba.grupo10.vfit.data

class DataSourceException(
    var code: Int,
    message: String,
    var details: List<String>?
) : Exception(message)