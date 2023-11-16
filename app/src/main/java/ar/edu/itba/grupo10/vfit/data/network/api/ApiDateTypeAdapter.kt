package ar.edu.itba.grupo10.vfit.data.network.api

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.util.Date

class ApiDateTypeAdapter : TypeAdapter<Date?>() {

    override fun read(`in`: JsonReader): Date? {
        return if (`in`.peek() != JsonToken.NULL) Date(`in`.nextLong()) else null
    }

    override fun write(out: JsonWriter, value: Date?) {
        if (value == null) out.nullValue() else out.value(value.time)
    }

}