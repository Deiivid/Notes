package es.deiividev.com.realmdb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.deiividev.com.realmdb.data.dao.NotesDao
import es.deiividev.com.realmdb.model.NoteData

class NotesViewModel(
    private val db: NotesDao
) : ViewModel() {


    fun getNotes() = db.getNotes()

    fun deleteNote(note: NoteData) {
        db.deleteNote(note)
    }

    fun updateNote(note: NoteData) {
        db.updateNote(note)
    }

    fun createNote(title: String, note: String, image: String? = null) {
        val note = NoteData().apply {
            this.title = title
            this.note = note
            this.imageUri = image
        }
        db.insertNote(note)

    }


    fun getNote(id: String): NoteData? {
        return db.getNoteById(id)
    }

}

class NoteViewModelFactory(
    private val db: NotesDao
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(
            db = db
        ) as T
    }
}