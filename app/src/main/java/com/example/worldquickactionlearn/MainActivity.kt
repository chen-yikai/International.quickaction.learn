package com.example.worldquickactionlearn

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.example.worldquickactionlearn.ui.theme.WorldquickactionlearnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorldquickactionlearnTheme {
                LaunchedEffect(Unit) {
                    setupDynamicShortcut()
                }
                MainScreen(intent)
            }
        }
    }

    private fun setupDynamicShortcut() {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            putExtra("action", "Intent from dynamic shortcut")
            action = Intent.ACTION_VIEW
        }
        val shortcut = ShortcutInfoCompat.Builder(this, "id1")
            .setShortLabel("Dynamic shortcut")
            .setLongLabel("This is a dynamic shortcut")
            .setIcon(
                IconCompat.createWithResource(
                    this,
                    R.drawable.baseline_emoji_food_beverage_24
                )
            )
            .setIntent(intent)
            .build()

        ShortcutManagerCompat.pushDynamicShortcut(this, shortcut)
    }
}

@Composable
fun MainScreen(intent: Intent) {
    var intentAction by remember { mutableStateOf("") }

    LaunchedEffect(intent) {
        intentAction = intent.getStringExtra("action") ?: "no action found"
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("quick action menu demo (Dynamic & Static)")
        Card(modifier = Modifier.padding(10.dp)) {
            Text(intentAction, modifier = Modifier.padding(10.dp))
        }
    }
}