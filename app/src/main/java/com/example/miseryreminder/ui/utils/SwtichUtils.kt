package com.example.miseryreminder.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DailyRepeatSwitch(
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(
                color = color.copy(alpha = 0.1f),
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.dp,
                color = color.copy(alpha = 0.2f),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .clickable(
                indication = null,
                interactionSource = null
            ) { onToggle(!isEnabled) }, //{ isRepeatEnabled = it }
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Daily Reminders",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2d3436)
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = if (isEnabled) "Active" else "Disabled",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isEnabled) color else Color(0xFF636e72)
                )
            )
        }

        Switch(
            checked = isEnabled,
            onCheckedChange = onToggle, //{ isRepeatEnabled = it }
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = color,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFFE0E0E0),
                checkedBorderColor = color,
                uncheckedBorderColor = Color(0xFFBDBDBD)
            ),
            modifier = Modifier.scale(0.9f)
        )
    }
}