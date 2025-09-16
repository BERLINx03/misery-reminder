package com.example.miseryreminder.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardWrapper(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    counter: String,
    isDarkMode: Boolean,
    label: String
) {
    Card(
        modifier = modifier
            .border(
                width = 1.dp,
                color = backgroundColor.copy(alpha = 0.2f),
                shape = RoundedCornerShape(18.dp)
            )
            .background(Color.Transparent),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)

                .padding(26.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = counter,
                modifier = Modifier.padding(8.dp),
                color = if(isDarkMode)Color(0xFFECF0F1) else Color(0xFF2d3436),
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.3f),
                        offset = Offset(1f, 1f),
                        blurRadius = 4f
                    )
                )
            )
            Text(
                text = label,
                color = if(isDarkMode)Color(0xFFECF0F1)   else Color(0xFF2d3436),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
            )
        }
    }
}