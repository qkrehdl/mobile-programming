package com.example.mp0801

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


@Composable
fun GreetingSurface(name: String, modifier: Modifier = Modifier) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }
}

@Composable
fun GreetingNoSurface(name: String, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(8.dp)
    val shadowElevationPx = with(LocalDensity.current) { 2.dp.toPx() }
    val backgroundColor = MaterialTheme.colorScheme.primary
    Text(
        text = "Hello $name!",
        color = contentColorFor(backgroundColor),
        modifier = modifier
            .graphicsLayer(shape = shape, shadowElevation = shadowElevationPx)
            .background(backgroundColor, shape)
            .border(1.dp, MaterialTheme.colorScheme.secondary, shape)
    )
}
