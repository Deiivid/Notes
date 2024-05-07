package es.deiividev.com.realmdb

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericAppBar(
    title: String,
    onIconClick: (() -> Unit)?,
    icon: @Composable() (() -> Unit)?,
    iconState: MutableState<Boolean>
) {
    TopAppBar(
        title = { Text(title) },
        actions = {
            IconButton(
                onClick = { onIconClick?.invoke() },
                content = {
                    if (iconState.value) {
                        icon?.invoke()
                    }
                }
            )
        }

    )

}