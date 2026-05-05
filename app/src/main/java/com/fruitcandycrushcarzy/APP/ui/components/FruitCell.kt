package com.fruitcandycrushcarzy.APP.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fruitcandycrushcarzy.APP.game.model.Fruit
import com.fruitcandycrushcarzy.APP.game.model.SpecialType
import com.fruitcandycrushcarzy.APP.game.viewmodel.DragDirection
import kotlin.math.abs
import kotlin.random.Random

@Composable
fun FruitCell(
    fruit: Fruit?,
    isSelected: Boolean,
    onClick: () -> Unit,
    onSwipe: (DragDirection) -> Unit,
    modifier: Modifier = Modifier
) {
    var dragAccumulated by remember { mutableStateOf(Offset.Zero) }
    var swiped by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.2f else 1f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 500f),
        label = "selectionScale"
    )

    val specialBorder = when (fruit?.special) {
        SpecialType.ROW_BLAST -> Color.Cyan
        SpecialType.COL_BLAST -> Color.Magenta
        SpecialType.BOMB -> Color.Yellow
        else -> Color.Transparent
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .background(
                color = if (isSelected) Color.White.copy(alpha = 0.4f) else Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            )
            .then(
                if (specialBorder != Color.Transparent) {
                    Modifier.border(2.dp, specialBorder, RoundedCornerShape(12.dp))
                } else Modifier
            )
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        dragAccumulated = Offset.Zero
                        swiped = false
                    },
                    onDrag = { _, dragAmount ->
                        if (!swiped) {
                            dragAccumulated += dragAmount
                            val threshold = 40f
                            if (abs(dragAccumulated.x) > threshold || abs(dragAccumulated.y) > threshold) {
                                swiped = true
                                if (abs(dragAccumulated.x) > abs(dragAccumulated.y)) {
                                    if (dragAccumulated.x > 0) onSwipe(DragDirection.RIGHT) else onSwipe(DragDirection.LEFT)
                                } else {
                                    if (dragAccumulated.y > 0) onSwipe(DragDirection.DOWN) else onSwipe(DragDirection.UP)
                                }
                            }
                        }
                    }
                )
            }
            .clickable(onClick = onClick)
            .scale(scale),
        contentAlignment = Alignment.Center
    ) {
        // Particle Burst Effect on Clear
        var showParticles by remember { mutableStateOf(false) }
        
        LaunchedEffect(fruit) {
            if (fruit == null) {
                showParticles = true
            } else {
                showParticles = false
            }
        }

        if (showParticles) {
            ParticleBurst(color = Color.White.copy(alpha = 0.7f))
        }

        AnimatedVisibility(
            visible = fruit != null,
            enter = scaleIn(animationSpec = tween(300)),
            exit = scaleOut(animationSpec = tween(300))
        ) {
            if (fruit != null) {
                Text(
                    text = fruit.emoji,
                    fontSize = 32.sp
                )
            }
        }
    }
}

@Composable
fun ParticleBurst(color: Color) {
    val particles = remember {
        List(12) {
            ParticleData(
                angle = Random.nextFloat() * 360f,
                speed = Random.nextFloat() * 120f + 60f,
                size = Random.nextFloat() * 5f + 2f
            )
        }
    }

    val progress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing)
        )
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val center = Offset(size.width / 2, size.height / 2)
        particles.forEach { p ->
            val dist = p.speed * progress.value
            val rad = Math.toRadians(p.angle.toDouble())
            val x = center.x + dist * Math.cos(rad).toFloat()
            val y = center.y + dist * Math.sin(rad).toFloat()
            drawCircle(
                color = color.copy(alpha = (1f - progress.value).coerceAtLeast(0f)),
                radius = p.size * (1f - progress.value),
                center = Offset(x, y)
            )
        }
    }
}

private data class ParticleData(val angle: Float, val speed: Float, val size: Float)
