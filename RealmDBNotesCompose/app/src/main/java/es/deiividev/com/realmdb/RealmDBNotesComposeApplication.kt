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
    private var db: Realm? = null  // Cambia la variable para almacenar una instancia de Realm, no de RealmConfiguration

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        // No hay necesidad de llamar a Realm.init, Realm.open ya lo maneja internamente
    }

    private fun getDb(): Realm {
        if (db == null) {
            val config = RealmConfiguration.Builder(schema = setOf(NoteData::class))  // Asegúrate de añadir todos tus modelos Realm
                .schemaVersion(1)
                .build()
            db = Realm.open(config)  // Abre y guarda la instancia de Realm con la configuración
        }
        return db!!
    }

    companion object {
        private var instance: RealmDBNotesComposeApplication? = null

        fun getDao(): NotesDao {
            return RealmDBNotes(instance!!.getDb())  // Asegúrate de que RealmDBNotes puede aceptar Realm como parámetro
        }

        fun getUriPermission(uri: Uri) {
            instance!!.applicationContext.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
    }
}