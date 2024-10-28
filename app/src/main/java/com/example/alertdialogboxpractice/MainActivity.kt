package com.example.alertdialogboxpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alertdialogboxpractice.ui.theme.AlertDialogBoxPracticeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlertDialogBoxPracticeTheme {
                dialogbox()
            }
        }
    }
}
data class Wishes(val Wish:String,val Day:String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dialogbox(modifier: Modifier = Modifier) {
    val show= remember {
        mutableStateOf(false)
    }
    val firsttext= remember {
        mutableStateOf("")
    }
    val secondtext= remember {
        mutableStateOf("")
    }
    val sitems= remember {
        mutableStateListOf<Wishes>()
    }
    val drawersstate= rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope= rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState =drawersstate,
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerItem(label = {
                    Text(text = "Account")
                }, selected = false, onClick = { /*TODO*/ })
                NavigationDrawerItem(label = {
                    Text(text = "Info")
                }, selected = false, onClick = { /*TODO*/ })
                NavigationDrawerItem(label = {
                    Text(text = "Contact")
                }, selected = false, onClick = { /*TODO*/ })
            }
        }) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { show.value=true },
                    modifier=Modifier.padding(18.dp)) {
                    Icon(imageVector = Icons.Default.Add, contentDescription =null )
                }
            },
            topBar = {
                TopAppBar(title = { Text(text = "WishList App") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawersstate.open()
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                        }
                    })
            }
        ) {
            Text(text = "",modifier=Modifier.padding(it))
        }
        LazyColumn(modifier=Modifier.padding(top = 80.dp)) {
            items(sitems){item ->

                look(wish = item,{
                    sitems.remove(item)
                })
            }
        }

    }

    if(show.value){
        AlertDialog(onDismissRequest = { show.value=false }, confirmButton = {
            Row(modifier=Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceAround) {
                Button(onClick = { sitems.add(Wishes(firsttext.value,secondtext.value))
                    show.value=false
                    firsttext.value=""
                    secondtext.value=""
                }) {
                    Text(text = "Confirm")
                }
                Spacer(modifier = Modifier.width(12.dp))
                Button(onClick = { show.value=false
                    firsttext.value=""
                    secondtext.value=""}) {
                    Text(text = "Cancel")
                }
            }

        },
            text = {
                Column {
                   TextField(value = firsttext.value, onValueChange ={
                        firsttext.value=it
                    },
                       label = {
                           Text(text = "Enter your Wish")
                       })
                    Spacer(modifier = Modifier.height(12.dp))
                    TextField(value = secondtext.value, onValueChange ={
                        secondtext.value=it
                    },
                        label = {
                            Text(text = "Enter the day")
                        })
                }

            },
            icon = {
                Icon(imageVector = Icons.Default.Home, contentDescription = null)
            },
            shape = RoundedCornerShape(12.dp),
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    }



}

@Composable
fun look(wish:Wishes,delete:()->Unit) {
    Row(modifier = Modifier
        .padding(top = 12.dp)
        .fillMaxWidth()
        .background(color = Color.LightGray, shape = RoundedCornerShape(12.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text =wish.Wish, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Blue )
            Text(text = wish.Day, fontWeight = FontWeight.Medium, fontSize = 16.sp, fontStyle = FontStyle.Italic)
        }
        IconButton(onClick = {
            delete()
        }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription =null )
        }
    }

    
}
