package com.example.texttalk.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.texttalk.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomAppBar(
    cameraClick: () -> Unit = {},
    galleryClick: () -> Unit = {},
) {
    // Gradient colors for the background
    val gradientColors = listOf(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.secondaryContainer
    )

    BottomAppBar(
        modifier = Modifier
            .shadow(elevation = 16.dp, shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .height(120.dp),
        containerColor = Color.Transparent,
        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 2.dp),
        tonalElevation = 0.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(gradientColors),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Gallery Button
                BottomAppBarIconButton(
                    icon = R.drawable.photo,
                    onClick = galleryClick,
                    contentDescription = "Open Gallery",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Camera Button
                BottomAppBarIconButton(
                    icon = R.drawable.camer,
                    onClick = cameraClick,
                    contentDescription = "Open Camera",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,

                )
            }
        }
    }
}



    @Composable
    private fun BottomAppBarIconButton(
        @DrawableRes icon: Int,
        onClick: () -> Unit,
        contentDescription: String,
        tint: Color
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(60.dp)
                .shadow(4.dp, CircleShape)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = contentDescription,
                tint = tint,
                modifier = Modifier.size(28.dp)
            )
        }
    }