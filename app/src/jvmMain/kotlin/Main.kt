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

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import io.github.kdroidfilter.nucleus.aot.runtime.AotRuntime
import io.github.kdroidfilter.nucleus.window.DecoratedWindow
import io.github.kdroidfilter.nucleus.window.NucleusDecoratedWindowTheme
import io.github.kdroidfilter.nucleus.window.TitleBar
import io.github.kdroidfilter.nucleus.window.newFullscreenControls
import ui.icons.AppIcon
import kotlin.system.exitProcess

fun main() {

    if (AotRuntime.isTraining()) {
        Thread({
            Thread.sleep(30_000)
            exitProcess(0)
        }, "aot-timer").apply {
            isDaemon = true
            start()
        }
    }

    application {

        println("$APP_NAME $APP_VERSION")

        NucleusDecoratedWindowTheme(isDark = false) {

            DecoratedWindow(
                onCloseRequest = ::exitApplication,
                state = rememberWindowState(
                    size = DpSize(352.dp, 810.dp)
                ),
                resizable = false,
                title = APP_NAME,
                icon = rememberVectorPainter(AppIcon)
            ) {

                TitleBar(modifier = Modifier.newFullscreenControls()) { _ ->
                    Text(
                        title,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(horizontal = 16.dp),
                        color = MaterialTheme.colors.onSurface,
                    )
                }

                App()
            }
        }
    }
}
