/*
 * PixelSafe - Steganography tool for PNG images
 * Copyright (C) 2025 Stefan Oltmann
 * https://stefan-oltmann.de/pixelsafe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun MainView() {

    val scrollState = rememberScrollState()

    val currentToast = remember { mutableStateOf<Toast?>(null) }

    val blockUserInput = remember { mutableStateOf(false) }

    /*
     * Reset toast messages
     */
    LaunchedEffect(currentToast.value) {

        if (currentToast.value != null) {

            delay(3000L)

            currentToast.value = null
        }
    }

    Box {

        ContentView(
            scrollState = scrollState,
            blockUserInput = blockUserInput,
            showToast = { toast ->
                currentToast.value = toast
            }
        )

        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState),
            modifier = Modifier.fillMaxHeight().align(Alignment.CenterEnd),
            style = defaultScrollbarStyle().copy(
                unhoverColor = Color.Black.copy(alpha = 0.4f),
                hoverColor = Color.Black
            )
        )

        if (blockUserInput.value) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.8f))
                    .fillMaxSize()
                    .clickable {
                        /* Block user input */
                    }
            ) {

                Text(
                    text = "Working ...",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        AnimatedVisibility(
            visible = currentToast.value != null,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(320.dp)
                .padding(bottom = 16.dp)
        ) {

            val toast = currentToast.value ?: return@AnimatedVisibility

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .shadow(
                        elevation = 8.dp,
                        shape = MaterialTheme.shapes.medium,
                        ambientColor = Color.Black,
                        spotColor = Color.Black
                    )
                    .background(
                        Color.White,
                        MaterialTheme.shapes.medium
                    )
            ) {

                Text(
                    text = toast.message,
                    color = if (toast.type == ToastType.SUCCESS)
                        Color(0xFF006400)
                    else
                        Color.Red,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
