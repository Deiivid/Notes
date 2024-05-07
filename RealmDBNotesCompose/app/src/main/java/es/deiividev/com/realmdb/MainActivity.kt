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
import es.deiividev.com.realmdb.ui.theme.RealmDBNotesComposeTheme


class MainActivity : ComponentActivity() {

    private lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            NoteViewModelFactory(RealmDBNotesComposeApplication.getDao()).create(NotesViewModel::class.java)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Constants.NAVIGATION_NOTES_LIST
            ) {
                // Notes List
                composable(Constants.NAVIGATION_NOTES_LIST) {
                    NoteListScreen(
                        navController = navController,
                        viewModel = viewModel
                    )
                }

                // note detail page
                composable(
                    Constants.NAVIGATION_NOTES_DETAIL,
                    arguments = listOf(navArgument(Constants.NAVIGATION_NOTES_ID_ARGUMENT) {
                        type = NavType.StringType
                    })
                ) { navBackStackEntry ->
                    navBackStackEntry.arguments?.getString(Constants.NAVIGATION_NOTES_ID_ARGUMENT)
                        ?.let {
                            NoteDetailPage(
                                noteId = it,
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                }

                // note edit page
                composable(
                    Constants.NAVIGATION_NOTES_EDIT,
                    arguments = listOf(navArgument(Constants.NAVIGATION_NOTES_ID_ARGUMENT) {
                        type = NavType.StringType
                    })
                ) { navBackStackEntry ->
                    navBackStackEntry.arguments?.getString(Constants.NAVIGATION_NOTES_ID_ARGUMENT)
                        ?.let {
                            NoteEditScreen(
                                noteId = it,
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                }

                // note create page
                composable(Constants.NAVIGATION_NOTES_CREATE) {
                    CreateNoteScreen(navController = navController, viewModel = viewModel)
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