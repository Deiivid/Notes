package es.deiividev.com.realmdb.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

open class NoteData(
) : RealmObject {
    @PrimaryKey
    var id =  UUID.randomUUID().toString()
    var note: String = ""
    var title: String = ""
    var dateUpdated: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    var imageUri: String? = null
    var isPlaceholder: Boolean = false
}

fun NoteData.getDay(): String {
    if (this.dateUpdated.isEmpty()) return ""
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return LocalDateTime.parse(this.dateUpdated, formatter).toLocalDate()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}

val placeHolderList =
    listOf(NoteData().apply {  title = "No Notes found"; note = "Please create a note"; isPlaceholder = true; id =
        "0"
    })

fun List<NoteData>?.orPlaceHolderList(): List<NoteData> {
    return if (this != null && this.isNotEmpty()) {
        this
    } else placeHolderList
}









