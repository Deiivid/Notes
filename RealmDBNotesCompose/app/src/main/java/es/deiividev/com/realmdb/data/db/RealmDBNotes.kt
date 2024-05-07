package es.deiividev.com.realmdb.data.db

import es.deiividev.com.realmdb.data.dao.NotesDao
import es.deiividev.com.realmdb.model.NoteData
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy

class RealmDBNotes(private val realm: Realm) : NotesDao {

    override fun getNoteById(id: String): NoteData? {
        val query = realm.query(NoteData::class, "id == $0", id)
        return query.first().find()
    }

    override fun getNotes(): List<NoteData> {
        return realm.query(NoteData::class).find()
    }

    override fun deleteNote(note: NoteData) {
        realm.writeBlocking {
            findLatest(note)?.let { delete(it) }
        }
    }

    override fun updateNote(note: NoteData) {
        realm.writeBlocking {
            copyToRealm(note, updatePolicy = UpdatePolicy.ALL)
        }
    }

    override fun insertNote(note: NoteData) {
        realm.writeBlocking {
            copyToRealm(note)
        }
    }
}