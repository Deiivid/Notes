package es.deiividev.com.realmdb.NoteEdit

import es.deiividev.com.realmdb.NotesViewModel
import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

import es.deiividev.com.realmdb.GenericAppBar
import es.deiividev.com.realmdb.NoteList.NotesFab
import es.deiividev.com.realmdb.theme.PhotoNotesTheme
import es.deiividev.com.realmdb.Constants
import es.deiividev.com.realmdb.R
import es.deiividev.com.realmdb.RealmDBNotesComposeApplication
import es.deiividev.com.realmdb.model.NoteData

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteEditScreen(
    noteId: String,
    navController: NavController,
    viewModel: NotesViewModel
) {
    val note = remember { mutableStateOf(Constants.noteDetailPlaceHolder) }

    val currentNote = remember {
        mutableStateOf(note.value.note)
    }
    val currentTitle = remember {
        mutableStateOf(note.value.title)
    }
    val currentPhotos = remember {
        mutableStateOf(note.value.imageUri)
    }
    val saveButtonState = remember {
        mutableStateOf(false)
    }

    val getImageRequest =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument(),
            onResult = { uri ->
                if (uri != null) {
                    RealmDBNotesComposeApplication.getUriPermission(uri)
                }
                currentPhotos.value = uri.toString()
                if (currentPhotos.value != note.value.imageUri) {
                    saveButtonState.value = true
                }
            })

    LaunchedEffect(true) {
            note.value = viewModel.getNote(noteId) ?: Constants.noteDetailPlaceHolder
            currentNote.value = note.value.note
            currentTitle.value = note.value.title
            currentPhotos.value = note.value.imageUri
    }

    PhotoNotesTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Scaffold(
                topBar = {
                    GenericAppBar(
                        title = "Edit Note",
                        onIconClick = {
                            viewModel.updateNote(
                                NoteData().apply {
                                    id = note.value.id
                                    this.note = currentNote.value
                                    title = currentTitle.value
                                    imageUri = currentPhotos.value
                                }
                            )
                            navController.popBackStack()
                        },
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.save),
                                contentDescription = stringResource(id = R.string.save_note),
                                tint = Color.Black
                            )
                        },
                        iconState = saveButtonState
                    )
                },
                floatingActionButton = {
                    NotesFab(
                        contentDescription = stringResource(id = R.string.add_photo),
                        action = {
                            getImageRequest.launch(arrayOf("image/*"))
                        },
                        icon = R.drawable.camera
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize()
                ) {
                    if (currentPhotos.value != null && currentPhotos.value!!.isNotEmpty()) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest
                                    .Builder(LocalContext.current)
                                    .data(data = Uri.parse(currentPhotos.value))
                                    .build()
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxHeight(0.3f)
                                .fillMaxWidth()
                                .padding(6.dp),

                            contentScale = ContentScale.Crop
                        )
                    }

                    TextField(
                        value = currentTitle.value,
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = { value ->
                            currentTitle.value = value
                            if (currentTitle.value != note.value.title) {
                                saveButtonState.value = true
                            } else if (currentNote.value == note.value.note &&
                                currentTitle.value == note.value.title
                            ) {
                                saveButtonState.value = false
                            }
                        },
                        label = {Text("Title")}
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    TextField(
                        value = currentNote.value,
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = { value ->
                            currentNote.value = value
                            if (currentNote.value != note.value.note) {
                                saveButtonState.value = true
                            } else if (currentNote.value == note.value.note &&
                                currentTitle.value == note.value.title
                            ) {
                                saveButtonState.value = false
                            }
                        },
                        label = {Text("Body")}
                    )
                }
            }
        }
    }


}