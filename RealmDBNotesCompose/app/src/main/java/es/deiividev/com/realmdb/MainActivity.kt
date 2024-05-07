package es.deiividev.com.realmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import es.deiividev.com.realmdb.NoteCreate.CreateNoteScreen
import es.deiividev.com.realmdb.NoteDetail.NoteDetailPage
import es.deiividev.com.realmdb.NoteEdit.NoteEditScreen

import es.deiividev.com.realmdb.NoteList.NoteListScreen
import es.deiividev.com.realmdb.data.db.RealmDBNotes
import es.deiividev.com.realmdb.ui.theme.RealmDBNotesComposeTheme


class MainActivity : ComponentActivity() {

    private lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Acceder a la instancia de Realm desde la aplicación
        val realm = (application as RealmDBNotesComposeApplication).realm
        val dao = RealmDBNotes(realm)  // Crear una instancia de DAO pasando la instancia de Realm
        val factory = NoteViewModelFactory(dao)  // Usar una fábrica que toma el DAO como dependencia
        viewModel = factory.create(NotesViewModel::class.java)  // Crear el viewModel con la fábrica

        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Constants.NAVIGATION_NOTES_LIST
            ) {
                composable(Constants.NAVIGATION_NOTES_LIST) {
                    NoteListScreen(navController, viewModel)
                }
                composable(
                    Constants.NAVIGATION_NOTES_DETAIL,
                    arguments = listOf(navArgument(Constants.NAVIGATION_NOTES_ID_ARGUMENT) {
                        type = NavType.StringType
                    })
                ) { navBackStackEntry ->
                    navBackStackEntry.arguments?.getString(Constants.NAVIGATION_NOTES_ID_ARGUMENT)?.let {
                        NoteDetailPage(it, navController, viewModel)
                    }
                }
                composable(
                    Constants.NAVIGATION_NOTES_EDIT,
                    arguments = listOf(navArgument(Constants.NAVIGATION_NOTES_ID_ARGUMENT) {
                        type = NavType.StringType
                    })
                ) { navBackStackEntry ->
                    navBackStackEntry.arguments?.getString(Constants.NAVIGATION_NOTES_ID_ARGUMENT)?.let {
                        NoteEditScreen(it, navController, viewModel)
                    }
                }
                composable(Constants.NAVIGATION_NOTES_CREATE) {
                    CreateNoteScreen(navController, viewModel)
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RealmDBNotesComposeTheme {
        Greeting("Android")
    }
}