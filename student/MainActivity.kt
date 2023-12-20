package th.ac.kku.cis.student

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight // Bold
import androidx.compose.ui.text.style.TextAlign // TextCenter
import androidx.compose.ui.unit.sp // fontSize
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import th.ac.kku.cis.student.ui.theme.StudentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentTheme {
                // A surface container using the 'background' color from the theme
                StudentApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentApp(viewModel: StudentViewModel = viewModel()) {
    var isShowDialog by remember { mutableStateOf(false) }
    var newItemName by remember { mutableStateOf("") }
    var newItemStudentId by remember { mutableStateOf("") }

    if (isShowDialog) {
        InputDialog(
            itemName = newItemName,
            itemStudentId = newItemStudentId,
            onCancel = {
                isShowDialog = false
            },
            onAddButtonClick = { newItem, newStudentId ->
                viewModel.addStudent(newItem, newStudentId)
                isShowDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Student App",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp), // Adjust padding as needed
                        style = TextStyle(
                            fontWeight = FontWeight.Bold, // Bold
                            fontSize = 25.sp, // fontSize
                            textAlign = TextAlign.Center // TextCenter
                        )
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                )
            )
        }
        ,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isShowDialog = true },
                containerColor = Color.Blue,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, "Add new student", tint = Color.White)
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            items(viewModel.data) { student ->
                CartItem(name = student.name, studentId = student.studentId)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputDialog(
    itemName: String,
    itemStudentId: String,
    onCancel: () -> Unit,
    onAddButtonClick: (String, String) -> Unit
) {
    Dialog(
        onDismissRequest = onCancel,
    ) {
        var textValue1 by remember {
            mutableStateOf(itemName)
        }
        var textValue2 by remember {
            mutableStateOf(itemStudentId)
        }
        Card(
            modifier = Modifier.padding(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                OutlinedTextField(
                    value = textValue1,
                    onValueChange = { textValue1 = it },
                    label = { Text("Student Name") }
                )
                OutlinedTextField(
                    value = textValue2,
                    onValueChange = { textValue2 = it },
                    label = { Text("Student Id", ) }
                )
                TextButton(
                    onClick = { onAddButtonClick(textValue1, textValue2) },
                    colors =  ButtonDefaults.buttonColors(
                        containerColor = Color.Blue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Text(
                        "Add",
                    )
                }
            }
        }
    }
}

@Composable
private fun CartItem(name: String, studentId: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        Text(
            "$name",
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)
        )
        Text(
            "$studentId",
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    StudentTheme {
        StudentApp()
    }
}
