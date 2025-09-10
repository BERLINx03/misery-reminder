package com.example.miseryreminder.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import com.example.miseryreminder.AlarmSchedular

@Composable
fun MainScreen(alarmManager: AlarmSchedular, deviceName: String, daysElapsed: Long) {
    val context = LocalContext.current
    var hours by remember {
        mutableIntStateOf(0)
    }
    var mins by remember {
        mutableIntStateOf(0)
    }

    val infiniteTransition = rememberInfiniteTransition()
    val rotationAnimation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(5000, easing = LinearEasing))
    )
    val category = DangerCategory.getDangerCategory(daysElapsed)

    val animatedBrush by remember(category) {
        derivedStateOf {
            Brush.sweepGradient(
                colors = when (category) {
                    DangerCategory.SAFE_ZONE -> listOf(
                        Color(0xFF66BB6A),
                        Color(0xFF4CAF50),
                        Color(0xFF388E3C),
                        Color(0xFF66BB6A)
                    )

                    DangerCategory.COMFORTABLE -> listOf(
                        Color(0xFFA5D6A7),
                        Color(0xFF8BC34A),
                        Color(0xFF689F38),
                        Color(0xFFA5D6A7)
                    )

                    DangerCategory.ATTENTION_NEEDED -> listOf(
                        Color(0xFFFFF176),
                        Color(0xFFFFC107),
                        Color(0xFFF57F17),
                        Color(0xFFFFF176)
                    )

                    DangerCategory.ACTION_REQUIRED -> listOf(
                        Color(0xFFFFAB40),
                        Color(0xFFFF5722),
                        Color(0xFFD84315),
                        Color(0xFFFFAB40)
                    )

                    DangerCategory.CRITICAL -> listOf(
                        Color(0xFFFF7043),
                        Color(0xFFF44336),
                        Color(0xFFC62828),
                        Color(0xFFFF7043)
                    )

                    DangerCategory.OVERDUE -> listOf(
                        Color(0xFFE53935),
                        Color(0xFFD32F2F),
                        Color(0xFFB71C1C),
                        Color(0xFFE53935)
                    )
                }
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hala ya :$deviceName holder", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier =
                Modifier
                    .fillMaxWidth(0.9f)
                    .height(350.dp)
                    .drawBehind {
                        val shadowColor = when (category) {
                            DangerCategory.SAFE_ZONE -> Color(0xFF4CAF50)
                            DangerCategory.COMFORTABLE -> Color(0xFF8BC34A)
                            DangerCategory.ATTENTION_NEEDED -> Color(0xFFFFC107)
                            DangerCategory.ACTION_REQUIRED -> Color(0xFFFF5722)
                            DangerCategory.CRITICAL -> Color(0xFFF44336)
                            DangerCategory.OVERDUE -> Color(0xFFD32F2F)
                        }

                        drawCircle(
                            color = shadowColor.copy(alpha = 0.3f),
                            radius = size.minDimension / 2,
                            center = center.copy(x = center.x + 8f, y = center.y + 8f),
                            style = Stroke(68f)
                        )
                        rotate(rotationAnimation.value) {
                            drawCircle(animatedBrush, style = Stroke(80f))
                        }
                    }
                    .clip(CircleShape)
                    .padding(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${daysElapsed}",
                color = when (category) {
                    DangerCategory.SAFE_ZONE -> Color(0xFF4CAF50)
                    DangerCategory.COMFORTABLE -> Color(0xFF8BC34A)
                    DangerCategory.ATTENTION_NEEDED -> Color(0xFFFFC107)
                    DangerCategory.ACTION_REQUIRED -> Color(0xFFFF5722)
                    DangerCategory.CRITICAL -> Color(0xFFF44336)
                    DangerCategory.OVERDUE -> Color(0xFFD32F2F)
                },
                fontSize = 46.sp,
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
        }
        Spacer(Modifier.height(20.dp))

        WheelTimePicker(
            size = DpSize(200.dp, 120.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            selectorProperties = WheelPickerDefaults.selectorProperties(
                enabled = true,
                shape = RoundedCornerShape(15.dp),
                color = Color(0xFFf1faee).copy(alpha = 0.2f),
                border = BorderStroke(30.dp, when (category) {
                    DangerCategory.SAFE_ZONE -> Color(0xFF4CAF50).copy(alpha = 0.2f)
                    DangerCategory.COMFORTABLE -> Color(0xFF8BC34A).copy(alpha = 0.2f)
                    DangerCategory.ATTENTION_NEEDED -> Color(0xFFFFC107).copy(alpha = 0.2f)
                    DangerCategory.ACTION_REQUIRED -> Color(0xFFFF5722).copy(alpha = 0.2f)
                    DangerCategory.CRITICAL -> Color(0xFFF44336).copy(alpha = 0.2f)
                    DangerCategory.OVERDUE -> Color(0xFFD32F2F).copy(alpha = 0.2f)
                })
            )
        ) {
            hours = it.hour
            mins = it.minute
        }
        ReminderButton(
            category = category,
            hours = hours,
            mins = mins,
            context = context,
            alarmManager = alarmManager
        )
    }
}