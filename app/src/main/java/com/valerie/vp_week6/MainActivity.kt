package com.valerie.vp_week6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.valerie.vp_week6.ui.screens.MainScreen
import com.valerie.vp_week6.ui.theme.Vp_week6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Vp_week6Theme {
                MainScreen()
            }
        }
    }
}
