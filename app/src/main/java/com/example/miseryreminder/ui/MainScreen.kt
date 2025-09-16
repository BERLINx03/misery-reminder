package com.example.miseryreminder.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.miseryreminder.alarm.AlarmSchedular
import com.example.miseryreminder.ui.utils.CancelButton
import com.example.miseryreminder.ui.utils.CardWrapper
import com.example.miseryreminder.ui.utils.DailyRepeatSwitch
import com.example.miseryreminder.ui.utils.ReminderButton

@Composable
fun MainScreen(alarmManager: AlarmSchedular, daysElapsed: Long, hustledDays: Int, applications: Int, isDarkMode: Boolean) {
    val context = LocalContext.current
    var hours by rememberSaveable {
        mutableIntStateOf(0)
    }
    var mins by rememberSaveable {
        mutableIntStateOf(0)
    }

    var isAlarmSet by remember {
        mutableStateOf(alarmManager.isAlarmSet())
    }

    LaunchedEffect(alarmManager) {
        isAlarmSet = alarmManager.isAlarmSet()
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
                    DangerCategory.SAFE_ZONE -> if (isDarkMode) {
                        listOf(
                            Color(0xFF81C784), Color(0xFF66BB6A), Color(0xFF4CAF50), Color(0xFF81C784)
                        )
                    } else {
                        listOf(
                            Color(0xFF66BB6A), Color(0xFF4CAF50), Color(0xFF388E3C), Color(0xFF66BB6A)
                        )
                    }

                    DangerCategory.COMFORTABLE -> if (isDarkMode) {
                        listOf(
                            Color(0xFF9CCC65), Color(0xFF8BC34A), Color(0xFF7CB342), Color(0xFF9CCC65)
                        )
                    } else {
                        listOf(
                            Color(0xFFA5D6A7), Color(0xFF8BC34A), Color(0xFF689F38), Color(0xFFA5D6A7)
                        )
                    }

                    DangerCategory.ATTENTION_NEEDED -> if (isDarkMode) {
                        listOf(
                            Color(0xFFFFD54F), Color(0xFFFFC107), Color(0xFFFF8F00), Color(0xFFFFD54F)
                        )
                    } else {
                        listOf(
                            Color(0xFFFFF176), Color(0xFFFFC107), Color(0xFFF57F17), Color(0xFFFFF176)
                        )
                    }

                    DangerCategory.ACTION_REQUIRED -> if (isDarkMode) {
                        listOf(
                            Color(0xFFFFB74D), Color(0xFFFF7043), Color(0xFFFF5722), Color(0xFFFFB74D)
                        )
                    } else {
                        listOf(
                            Color(0xFFFFAB40), Color(0xFFFF5722), Color(0xFFD84315), Color(0xFFFFAB40)
                        )
                    }

                    DangerCategory.CRITICAL -> if (isDarkMode) {
                        listOf(
                            Color(0xFFFF8A65), Color(0xFFFF5722), Color(0xFFE64A19), Color(0xFFFF8A65)
                        )
                    } else {
                        listOf(
                            Color(0xFFFF7043), Color(0xFFF44336), Color(0xFFC62828), Color(0xFFFF7043)
                        )
                    }

                    DangerCategory.OVERDUE -> if (isDarkMode) {
                        listOf(
                            Color(0xFFEF5350), Color(0xFFF44336), Color(0xFFD32F2F), Color(0xFFEF5350)
                        )
                    } else {
                        listOf(
                            Color(0xFFE53935), Color(0xFFD32F2F), Color(0xFFB71C1C), Color(0xFFE53935)
                        )
                    }
                }
            )
        }
    }

    val marginColor = when (category) {
        DangerCategory.SAFE_ZONE -> Color(0xFF4CAF50)
        DangerCategory.COMFORTABLE -> Color(0xFF8BC34A)
        DangerCategory.ATTENTION_NEEDED -> Color(0xFFFFC107)
        DangerCategory.ACTION_REQUIRED -> Color(0xFFFF5722)
        DangerCategory.CRITICAL -> Color(0xFFF44336)
        DangerCategory.OVERDUE -> Color(0xFFD32F2F)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(marginColor.copy(alpha = 0.1f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        Box(modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(250.dp)
            .drawBehind {
                val shadowColor = marginColor
                drawCircle(
                    color = marginColor.copy(alpha = .5f), radius = size.minDimension / 2 - 40f
                )
                drawCircle(
                    color = shadowColor.copy(alpha = 0.04f),
                    radius = size.minDimension / 2 + 30f,
                    center = center.copy(x = center.x + 1f, y = center.y + 1f),
                    style = Stroke(78f)
                )
                rotate(rotationAnimation.value) {
                    drawCircle(animatedBrush, style = Stroke(80f))
                }
            }
            .clip(CircleShape), contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$daysElapsed",
                    color = if(isDarkMode)Color(0xFFECF0F1)   else Color(0xFF2d3436) ,
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
                Text(
                    text = "DAYS UNEMPLOYED",
                    color = if(isDarkMode)Color(0xFFECF0F1)   else Color(0xFF2d3436),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
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
        }

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            CardWrapper(
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .padding(
                        start = 16.dp, end = 8.dp, bottom = 8.dp
                    ),
                backgroundColor = marginColor.copy(0.2f),
                counter = hustledDays.toString(),
                isDarkMode = isDarkMode,
                label = "HUSTLE DAYS"
            )
            CardWrapper(
                modifier = Modifier.padding(
                    start = 8.dp, end = 16.dp
                ),
                backgroundColor = marginColor.copy(0.2f),
                counter = "$applications",
                isDarkMode = isDarkMode,
                label = "APPLICATIONS"
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .border(
                    width = 1.dp,
                    color = marginColor.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(18.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(marginColor.copy(.2f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "⏰ Daily Reminder Time",
                    color = if(isDarkMode)Color(0xFFECF0F1)   else Color(0xFF2d3436),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 16.dp)
                )
                WheelTimePicker(
                    size = DpSize(200.dp, 120.dp),
                    textStyle = MaterialTheme.typography.titleLarge,
                    selectorProperties = WheelPickerDefaults.selectorProperties(
                        enabled = true,
                        shape = RoundedCornerShape(15.dp),
                        color = Color(0xFFf1faee).copy(alpha = 0.2f),
                        border = BorderStroke(1.dp, Color.White)
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    hours = it.hour
                    mins = it.minute
                }
            }
        }

        var isRepeatEnabled by remember { mutableStateOf(false) }
        DailyRepeatSwitch(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            isEnabled = isRepeatEnabled,
            onToggle = { isRepeatEnabled = it },
            isDarkMode = isDarkMode,
            color = marginColor
        )

        CancelButton(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            alarmManager,
            marginColor,
            isDarkMode,
            isAlarmSet = isAlarmSet,
            onAlarmCancelled = { isAlarmSet = false }
        )
        ReminderButton(
            color = marginColor,
            hours = hours,
            mins = mins,
            repeat = isRepeatEnabled,
            context = context,
            isDarkMode = isDarkMode,
            alarmManager = alarmManager,
            onAlarmSet = { isAlarmSet = true }
        )
    }
}