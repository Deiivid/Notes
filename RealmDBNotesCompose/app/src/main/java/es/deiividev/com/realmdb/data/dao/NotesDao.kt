package es.deiividev.com.realmdb.data.dao

import es.deiividev.com.realmdb.model.NoteData

interface NotesDao {

    fun getNoteById(id: String): NoteData?


    fun getNotes(): List<NoteData>


    fun deleteNote(note: NoteData)


    fun updateNote(note: NoteData)


    fun insertNote(note: NoteData)
}