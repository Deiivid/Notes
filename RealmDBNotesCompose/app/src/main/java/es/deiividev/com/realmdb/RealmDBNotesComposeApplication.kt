package es.deiividev.com.realmdb

import android.app.Application
import android.content.Intent
import android.net.Uri
import es.deiividev.com.realmdb.data.dao.NotesDao
import es.deiividev.com.realmdb.data.db.RealmDBNotes
import es.deiividev.com.realmdb.model.NoteData
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class RealmDBNotesComposeApplication : Application() {
    lateinit var realm: Realm  // La instancia de Realm es pública

    override fun onCreate() {
        super.onCreate()
        val config = RealmConfiguration.Builder(schema = setOf(NoteData::class))
            .schemaVersion(1)
            .build()
        realm = Realm.open(config)  // Abre la base de datos de Realm
    }
    companion object {
        lateinit var instance: RealmDBNotesComposeApplication
            private set

        fun getDao(): NotesDao {
            // Retorna la implementación de Dao usando la instancia de Realm configurada
            return RealmDBNotes(instance.realm ?: throw IllegalStateException("Realm Database not initialized"))
        }

        fun getUriPermission(uri: Uri) {
            instance.applicationContext.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
    }
}
