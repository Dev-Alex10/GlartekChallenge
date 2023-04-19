package com.example.glartekchallenge.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.glartekchallenge.R
import com.example.glartekchallenge.ui.theme.GlartekChallengeTheme

/**
 * Implementation of App Widget functionality.
 */

class FavoriteWidget : GlanceAppWidget() {
    @Composable
    override fun Content() {
        GlartekChallengeTheme(darkTheme = false) {
            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .appWidgetBackground()
                    .background(color = Color.White)
            ) {
                Text(
                    text = "Favorite Movies",
                    modifier = GlanceModifier.fillMaxWidth(),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = ColorProvider(R.color.black)
                    )
                )
                FavoriteMoviesWidgetList()
            }
        }
    }
}

class FavoriteWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = FavoriteWidget()
}
