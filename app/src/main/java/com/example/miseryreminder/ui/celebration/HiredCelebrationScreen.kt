package com.example.miseryreminder.ui.celebration

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.compose.OnParticleSystemUpdateListener
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.PartySystem
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.core.models.Size
import java.util.concurrent.TimeUnit

@Composable
fun HiredCelebrationScreen(isHired: Boolean, content: @Composable () -> Unit) {
    var showCelebration by remember { mutableStateOf(false) }

    LaunchedEffect(isHired) {
        if (isHired) {
            showCelebration = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        content()

        if (showCelebration) {
            KonfettiView(
                modifier = Modifier.fillMaxSize(),
                parties = listOf(createHiredParty()),
                updateListener = object : OnParticleSystemUpdateListener {
                    override fun onParticleSystemEnded(system: PartySystem, activeSystems: Int) {
                        if (activeSystems == 0) {
                            showCelebration = false
                        }
                    }
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Green.copy(alpha = 0.9f)
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Text(
                        text = "Congratulations! 🎉",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(24.dp)
                    )
                }
            }
        }
    }
}

private fun createHiredParty(): Party {
    return Party(
        speed = 0f,
        maxSpeed = 30f,
        damping = 0.9f,
        spread = 360,
        colors = listOf(0xFFFFD700.toInt(), 0xFF32CD32.toInt(), 0xFF00FF00.toInt(), 0xFFADFF2F.toInt()),
        emitter = Emitter(duration = 3, TimeUnit.SECONDS).max(150),
        position = Position.Relative(0.5, 0.3),
        shapes = listOf(Shape.Square, Shape.Circle),
        size = listOf(Size.SMALL, Size.LARGE)
    )
}