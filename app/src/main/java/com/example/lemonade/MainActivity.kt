package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonadePreview()
            }
        }
    }
}

@Composable
fun DrinkLemonade() {

    var currentStep by remember { mutableStateOf(1) }

    var squeezeCount by remember { mutableStateOf(0) }

    when (currentStep) {
        1 -> {
            LemonTextAndImage(
                R.string.select_lemon,
                R.drawable.lemon_tree,
                R.string.lemon_tree,
                onImageClick = {
                    // Update to next step
                    currentStep = 2
                    // Each time a lemon is picked from the tree, get a new random number
                    // between 2 and 4 (inclusive) for the number of times the lemon needs
                    // to be squeezed to turn into lemonade
                    squeezeCount = (2..4).random()
                }
            )
        }
        2 -> {
            LemonTextAndImage(
                R.string.squeeze_lemon,
                R.drawable.lemon_squeeze,
                R.string.lemon,
                onImageClick = {
                    // Decrease the squeeze count by 1 for each click the user performs
                    squeezeCount--
                    // When we're done squeezing the lemon, move to the next step
                    if (squeezeCount == 0) {
                        currentStep = 3
                    }
                }
            )
        }
        3 -> {
            LemonTextAndImage(
                R.string.drink_lemonade,
                R.drawable.lemon_drink,
                R.string.lemonade_glass,
                onImageClick = {
                    // Update to next step
                    currentStep = 4
                }
            )
        }
        4 -> {
            LemonTextAndImage(
                R.string.start_again,
                R.drawable.lemon_restart,
                R.string.empty_glass,
                onImageClick = {
                    // Back to starting step
                    currentStep = 1
                }
            )
        }
    }
}

@Composable
fun LemonTextAndImage(
    textLabelResourceId: Int,
    drawableResourceId: Int,
    contentDescriptionResourceId: Int,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(textLabelResourceId),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(drawableResourceId),
            contentDescription = stringResource(contentDescriptionResourceId),
            modifier = Modifier
                .wrapContentSize()
                .clickable(
                    onClick = onImageClick
                )
                .border(
                    BorderStroke(2.dp, Color(105, 205, 216)),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadePreview() {
    LemonadeTheme {
        DrinkLemonade()
    }
}