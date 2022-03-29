package com.jetpack.animatefloatingactionmenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.animatefloatingactionmenu.ui.theme.AnimateFloatingActionMenuTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimateFloatingActionMenuTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Animate Floating Action Menu",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            )
                        }
                    ) {
                        Box {
                            var isVisible by remember { mutableStateOf(false) }

                            LazyGrid()
                            FloatingBottomActionMenu(isVisible = isVisible)
                            BottomAppBar(isVisible = isVisible) { isVisible = !isVisible }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyGrid() {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(10) { index ->
            val rnd = Random()
            val color: Int = android.graphics.Color.argb(255,
                rnd.nextInt(256),
                rnd.nextInt(256),
                rnd.nextInt(256)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .background(Color(color))
                    .padding(vertical = 32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cat),
                        contentDescription = "Grid Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.padding(5.dp))

                    Text(
                        text = "Text ${index + 1}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

private const val duration: Int = 1000
private val intOffsetTweenSpec: TweenSpec<IntOffset> = tween(durationMillis = duration)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BoxScope.FloatingBottomActionMenu(isVisible: Boolean) {
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { with(density) { 250.dp.roundToPx() } },
            animationSpec = intOffsetTweenSpec
        ),
        exit = slideOutVertically(
            targetOffsetY = { with(density) { 250.dp.roundToPx() } },
            animationSpec = intOffsetTweenSpec
        ),
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .height(250.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        0.5f to Color.White,
                        0.9f to Color.White.copy(alpha = 0.3f),
                        0.99f to Color.White.copy(alpha = 0.005f),
                        startY = Float.POSITIVE_INFINITY,
                        endY = 0.0f
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                PIconButton(
                    text = "Add Place",
                    imageVector = Icons.Default.Add,
                    modifier = Modifier
                        .weight(1f)
                        .animateEnterExit(
                            enter = slideInVertically(
                                initialOffsetY = { 50 },
                                animationSpec = intOffsetTweenSpec
                            ),
                            exit = slideOutVertically(
                                targetOffsetY = { 50 },
                                animationSpec = intOffsetTweenSpec
                            )
                        )
                )

                PIconButton(
                    text = "Create List",
                    imageVector = Icons.Default.List,
                    modifier = Modifier
                        .weight(1f)
                        .animateEnterExit(
                            enter = slideInVertically(
                                initialOffsetY = { 250 },
                                animationSpec = intOffsetTweenSpec
                            ),
                            exit = slideOutVertically(
                                targetOffsetY = { 250 },
                                animationSpec = intOffsetTweenSpec
                            )
                        )
                )

                PIconButton(
                    text = "Add Friend",
                    imageVector = Icons.Default.AccountBox,
                    modifier = Modifier
                        .weight(1f)
                        .animateEnterExit(
                            enter = slideInVertically(
                                initialOffsetY = { 450 },
                                animationSpec = intOffsetTweenSpec
                            ),
                            exit = slideOutVertically(
                                targetOffsetY = { 450 },
                                animationSpec = intOffsetTweenSpec
                            )
                        )
                )
            }
        }
    }
}

@Composable
fun RowScope.PIconButton(
    text: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
                .background(Color.Black)
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector,
                contentDescription = text,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(30.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text.uppercase(Locale.getDefault()),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption.copy(
                letterSpacing = 1.1.sp,
                fontWeight = FontWeight.Medium,
            )
        )
    }
}

private val floatTweenSpec: TweenSpec<Float> = tween(durationMillis = duration)
private val colorTweenSpec: TweenSpec<Color> = tween(durationMillis = duration)

@Composable
fun BoxScope.BottomAppBar(isVisible: Boolean, _onIconTap: () -> Unit) {
    val animatedIconFGColor by
    animateColorAsState(
        targetValue = if (isVisible) Color.Black else Color.White,
        animationSpec = colorTweenSpec
    )
    val animatedIconBGColor by
    animateColorAsState(
        targetValue = if (isVisible) Color(0xFFEAEAEA) else Color(0xFFFF3841),
        animationSpec = colorTweenSpec
    )
    val animatedRotateAngle by
    animateFloatAsState(
        targetValue = if (isVisible) 135.0f else 0.0f,
        animationSpec = floatTweenSpec
    )

    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
        elevation = 0.dp
    ) {
        Icon(
            Icons.Outlined.LocationOn,
            contentDescription = "Location",
            tint = Color.White,
            modifier = Modifier.weight(1f)
        )
        Icon(
            Icons.Outlined.Email,
            contentDescription = "Email",
            tint = Color.White,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .size(40.dp)
                    .align(Alignment.TopCenter)
                    .clip(CircleShape)
                    .background(animatedIconBGColor)
                    .clickable {
                        _onIconTap()
                    }
            )
            Icon(
                Icons.Outlined.Add,
                contentDescription = "Add",
                tint = animatedIconFGColor,
                modifier = Modifier
                    .size(35.dp)
                    .padding(bottom = 5.dp)
                    .align(Alignment.Center)
                    .rotate(animatedRotateAngle)

            )
        }
        Icon(
            Icons.Outlined.Notifications,
            contentDescription = "Notifications",
            tint = Color.White,
            modifier = Modifier.weight(1f)
        )
        Icon(
            Icons.Outlined.Person,
            contentDescription = "Person",
            tint = Color.White,
            modifier = Modifier.weight(1f)
        )
    }
}


























