package es.deiividev.com.realmdb.NoteCreate

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
import androidx.compose.material3.TopAppBar
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
import es.deiividev.com.realmdb.R
import es.deiividev.com.realmdb.RealmDBNotesComposeApplication

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateNoteScreen(
    navController: NavController,
    viewModel: NotesViewModel
) {

    val currentNote = remember {
        mutableStateOf("")
    }
    val currentTitle = remember {
        mutableStateOf("")
    }
    val currentPhotos = remember {
        mutableStateOf("")
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
            })

    PhotoNotesTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Scaffold(
                topBar = {
                    GenericAppBar(
                        title = "Create Note",
                        onIconClick = {
                            viewModel.createNote(
                              currentTitle.value,
                              currentNote.value,
                              currentPhotos.value
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
                        .padding(12.dp, top= 150.dp)
                        .fillMaxSize()
                ) {
                    if (currentPhotos.value.isNotEmpty()) {
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
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = { value ->
                            currentTitle.value = value
                            saveButtonState.value = currentTitle.value != "" && currentNote.value != ""
                        },
                        label = { Text("Title") }
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    TextField(
                        value = currentNote.value,
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            focusedLabelColor = Color.Black
                        ),
                        onValueChange = { value ->
                            currentNote.value = value

                            saveButtonState.value = currentTitle.value != "" && currentNote.value != ""
                        },
                        label = { Text("Body") }
                    )
                }
            }
        }
    }
    @Composable
    fun GenericAppBar(title: String, onIconClick: () -> Unit, icon: @Composable () -> Unit, iconState: MutableState<Boolean>) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    modifier = Modifier.padding(start = 8.dp)  // Agrega padding al inicio para separarlo de los íconos
                )
            },
            navigationIcon = {
                // Si necesitas un ícono de navegación, puedes agregarlo aquí
            },
            actions = {
                icon()  // Aquí pones tu ícono, ya sea de guardado o menú
                // Si tienes más íconos o acciones, añádelos aquí
            },
            modifier = Modifier.height(68.dp)  // Puedes ajustar la altura aquí si es necesario
        )
    }

}
















