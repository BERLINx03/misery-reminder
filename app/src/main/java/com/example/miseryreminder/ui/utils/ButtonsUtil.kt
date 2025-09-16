package com.example.miseryreminder.ui.utils

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.miseryreminder.alarm.AlarmSchedular
import com.example.miseryreminder.R


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ReminderButton(
    color: Color,
    hours: Int,
    mins: Int,
    repeat: Boolean,
    alarmManager: AlarmSchedular,
    isDarkMode: Boolean,
    context: Context,
    onAlarmSet: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var showSuccessAnimation by remember { mutableStateOf(false) }

    val buttonScale by animateFloatAsState(
        targetValue = if (isLoading) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    val successScale by animateFloatAsState(
        targetValue = if (showSuccessAnimation) 1.1f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        finishedListener = { if (showSuccessAnimation) showSuccessAnimation = false }
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Button(
            onClick = {
                isLoading = true
                alarmManager.setAlarmDaily(hours, mins, repeat) { done, calendar ->
                    isLoading = false
                    if (done) {
                        val currentTime = System.currentTimeMillis()
                        val timeDiff = calendar.timeInMillis - currentTime
                        val hoursUntil = timeDiff / (1000 * 60 * 60)
                        val minutesUntil = (timeDiff % (1000 * 60 * 60)) / (1000 * 60)
                        showSuccessAnimation = true
                        toast(
                            if (hoursUntil == 0L && minutesUntil == 0L)
                                "The alarm has been set for a few seconds from now."
                            else
                                "the alarm has been set for $hoursUntil hours and $minutesUntil minutes.",
                            context
                        )
                        onAlarmSet()
                    } else {
                        toast("Something wrong happen, please report it!", context)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .scale(buttonScale * successScale)
                .shadow(
                    elevation = if (isLoading) 2.dp else 6.dp,
                    shape = RoundedCornerShape(16.dp),
                    ambientColor = color.copy(alpha = 0.3f),
                    spotColor = color.copy(alpha = 0.3f)
                ),
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = color.copy(alpha = .5f),
                contentColor = if (isDarkMode) Color(0xFFECF0F1) else Color(0xFF2d3436),
                disabledContainerColor = color.copy(alpha = 0.6f),
                disabledContentColor = if (isDarkMode) Color(0xFFB2BEC3) else Color.White.copy(alpha = 0.7f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            AnimatedContent(
                targetState = isLoading,
                transitionSpec = {
                    (slideInVertically { -it } + fadeIn()).togetherWith(slideOutVertically { it } + fadeOut())
                }
            ) { loading ->
                if (loading) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "جاري الضبط...",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                } else {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Set reminder",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${String.format("%02d", hours)}:${String.format("%02d", mins)}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF2d3436)
                            ),
                            modifier = Modifier
                                .background(
                                    Color.White.copy(alpha = 0.5f),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CancelButton(
    modifier: Modifier = Modifier,
    alarmManager: AlarmSchedular,
    color: Color,
    isDarkMode: Boolean,
    isAlarmSet: Boolean,
    onAlarmCancelled: () -> Unit
) {
    Button(
        onClick = {
            alarmManager.cancelAlarms()
            onAlarmCancelled()
        },
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                width = 1.dp,
                color = color.copy(alpha = 0.2f),
                shape = RoundedCornerShape(18.dp)
            ),
        enabled = isAlarmSet,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isAlarmSet) color.copy(alpha = 0.2f) else if (isDarkMode) Color(0xFF1E1E1E) else Color(0xFFF8F9FA),
            contentColor = if (isAlarmSet) {
                if (isDarkMode) Color(0xFFECF0F1) else Color(0xFF2d3436)
            } else {
                if (isDarkMode) Color(0xFFB2BEC3) else Color(0xFF6C757D)
            },
            disabledContainerColor = if (isDarkMode) Color(0xFF2C2C2C) else Color(0xFFF5F5F5),
            disabledContentColor = if (isDarkMode) Color(0xFF6C757D) else Color(0xFFBDBDBD)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = "Cancel any scheduled alarms",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        )
    }
}