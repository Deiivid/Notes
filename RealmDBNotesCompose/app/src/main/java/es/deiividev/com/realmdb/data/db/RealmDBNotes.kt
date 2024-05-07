package es.deiividev.com.realmdb.data.db

import es.deiividev.com.realmdb.data.dao.NotesDao
import es.deiividev.com.realmdb.model.NoteData
import io.realm.kotlin.Realm

class RealmDBNotes(private val realm: Realm) : NotesDao {

    override fun getNoteById(id: String): NoteData? {
        // Buscar directamente usando queries en Kotlin
        return realm.query(NoteData::class, "id == $0", id).first().find()
    }

    override fun getNotes(): List<NoteData> {
        // Utilizar query() para obtener todos los datos y sort() para ordenarlos
        return realm.query(NoteData::class).sort("dateUpdated", io.realm.kotlin.query.Sort.DESCENDING).find()
    }

    override fun deleteNote(note: NoteData) {
        realm.writeBlocking {
            // Usar findLatest y delete
            findLatest(note)?.let { delete(it) }
        }
    }

    override fun updateNote(note: NoteData) {
        // En Realm Kotlin, update es simplemente un insert que sobrescribe en base a la clave primaria
        insertNote(note)
    }

    override fun insertNote(note: NoteData) {
        realm.writeBlocking {
            // Insertar o actualizar el objeto nota
            copyToRealm(note)
        }
    }
}