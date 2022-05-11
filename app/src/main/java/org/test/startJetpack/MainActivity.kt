package org.test.startJetpack

import android.os.Bundle
import android.util.Log
import android.widget.ScrollView
import android.widget.StackView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.test.startJetpack.ui.theme.StartJetpackTheme
import org.test.startJetpack.ui.theme.Teal200
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StartJetpackTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                    Start()
                }
            }
        }
    }
}

@Composable
fun dialog() {

}

@Composable
fun Start() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState();
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

    val (text, setText) = remember { mutableStateOf(TextFieldValue("")) }

    val mutableList = remember { mutableStateListOf<String>() }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Teal200,
            ) {
                Row {
                    Column {
                        Text(text = "leading")
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1F)
                    ) {
                        Text(text = "title")
                    }
                    Column {
                        Text(text = "action")
                    }
                }
            }
        }, content = {

            if (showDialog) {
                AlertDialog(onDismissRequest = { }, title = {
                    Text("Title")
                }, confirmButton = {
                    Button(onClick = { setShowDialog(false) }) {
                        Text(text = "ok")
                    }
                    Button(onClick = { setShowDialog(false) }) {
                        Text(text = "close")
                    }
                }, text = {
                    Text("显示的内容")
                });
            }


            Column(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .border(BorderStroke(2.dp, Color.Red))
            ) {
                Row(
                    Modifier
                        .border(BorderStroke(2.dp, Color.Blue))
                        .fillMaxWidth()
                ) {
                    Row {

                        Text("xxx1")
                        Text(
                            "xxx2",
                            Modifier
                                .weight(1f)
                                .background(color = Color.Red)
                        )
                    }
                }
                Row {
                    TextField(text, onValueChange = { newText ->
                        setText(newText)
                    }, Modifier.fillMaxWidth(1f))
                }
                Row(
                    Modifier
                        .background(color = Color.Cyan)
                        .fillMaxWidth()
                ) {
                    Row {
                        Button(

                            onClick = {
                                scope.launch {
                                    val result = scaffoldState.snackbarHostState
                                        .showSnackbar(
                                            message = "这是一个snakebar",
                                            actionLabel = "关闭",
                                            // Defaults to SnackbarDuration.Short
                                            duration = SnackbarDuration.Indefinite
                                        )
                                    when (result) {
                                        SnackbarResult.ActionPerformed -> {
                                            /* Handle snackbar action performed */
                                        }
                                        SnackbarResult.Dismissed -> {
                                            /* Handle snackbar dismissed */
                                        }
                                    }
                                }
                            }, Modifier.padding(5.dp)
                        ) {
                            Text("snakebar")
                        }

                        Button(onClick = {
                            scope.launch {
                                setShowDialog(true)
                            }
                        }, Modifier.padding(5.dp)) {
                            Text("dialog")
                        }

                        Button(onClick = {
                            scope.launch {
                                if (!text.text.equals("")) {
                                    Log.i("xxx", "Start: " + text.text)

                                    mutableList.add(0, text.text)
                                } else {
                                    setShowDialog(true)
                                }

                            }
                        }, Modifier.padding(5.dp)) {
                            Text("Add")
                        }
                    }
                }

                Column(

                    Modifier
                        .height(100.dp)
                        .verticalScroll(scrollState)
                        .weight(1f)
                ) {
                    mutableList.forEach { i ->
                        Text(text = i,
                            Modifier
                                .background(Color.Yellow)
                                .fillMaxWidth())
                    }

                }
                Row(
                    Modifier
                        .padding(PaddingValues(5.dp))
                        .border(BorderStroke(2.dp, Color.Yellow))
                        .background(Color.Blue)
                        .height(100.dp)
                        .fillMaxWidth()
                ) {
                    Text("xxx3")
                }
            }
        })

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StartJetpackTheme {
        Start()
    }
}