package com.jgomez.common_utils.ui.component.forms

import android.widget.EditText
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgomez.common_utils.navigation.BottomBarScreen.Discover.title

@Composable
fun EditText(){

    var name by remember {
        mutableStateOf("Carlos")
    }

    Row(Modifier.padding(5.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it }
        )
        IconButton(onClick = {})
        {
            Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "Send Icon")
        }
    }
}


@Composable
@Preview
fun EditTextPreview(){
    EditText()
}