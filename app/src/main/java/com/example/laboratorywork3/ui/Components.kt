package com.example.laboratorywork3.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laboratorywork3.model.Priority

val AppBlue = Color(0xFF2196F3)
val ScreenBg = Color(0xFFF5F5F5)

fun stripeColor(p: Priority): Color = when (p) {
    Priority.HIGH -> Color(0xFFF44336)
    Priority.MEDIUM -> Color(0xFFFF9800)
    Priority.LOW -> Color(0xFF4CAF50)
}

@Composable
fun AppTopBar(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppBlue)
            .statusBarsPadding()
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}

@Composable
fun PriorityBadge(priority: Priority) {
    val (bg, fg) = when (priority) {
        Priority.HIGH -> Color(0xFFFFCDD2) to Color(0xFFC62828)
        Priority.MEDIUM -> Color(0xFFFFE0B2) to Color(0xFFE65100)
        Priority.LOW -> Color(0xFFC8E6C9) to Color(0xFF2E7D32)
    }
    Text(
        text = priority.label.uppercase(),
        color = fg,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .background(bg, RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    )
}

@Composable
fun WhiteCard(
    modifier: Modifier = Modifier,
    stripe: Color? = null,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            Modifier
                .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
                .height(IntrinsicSize.Min)
        ) {
            if (stripe != null) {
                Box(Modifier.width(4.dp).fillMaxHeight().background(stripe))
            }
            Column(
                modifier = Modifier.weight(1f).padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = content
            )
        }
    }
}

@Composable
fun GrayButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFEEEEEE),
            contentColor = Color.Black,
            disabledContainerColor = Color(0xFFE0E0E0),
            disabledContentColor = Color(0xFF9E9E9E)
        ),
        border = BorderStroke(1.dp, Color(0xFFCCCCCC))
    ) {
        Text(text, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
    }
}
