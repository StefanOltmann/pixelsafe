package ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon: ImageVector
    get() {
        if (_AppIcon != null) {
            return _AppIcon!!
        }
        _AppIcon = ImageVector.Builder(
            name = "AppIcon",
            defaultWidth = 64.dp,
            defaultHeight = 64.dp,
            viewportWidth = 64f,
            viewportHeight = 64f
        ).apply {
            path(fill = SolidColor(Color(0xFF012C47))) {
                moveTo(34f, 38f)
                lineTo(30f, 38f)
                lineTo(30.81f, 33.53f)
                curveTo(29.91f, 33.1f, 29.35f, 32.2f, 29.35f, 31.14f)
                curveTo(29.35f, 29.66f, 30.5f, 28.44f, 31.97f, 28.44f)
                curveTo(33.45f, 28.44f, 34.65f, 29.64f, 34.65f, 31.12f)
                curveTo(34.65f, 32.16f, 34.06f, 33.06f, 33.19f, 33.5f)
                lineTo(34f, 38f)
                close()
                moveTo(41.39f, 25f)
                lineTo(22.64f, 25f)
                curveTo(21.76f, 25f, 21.01f, 25.74f, 21.01f, 26.62f)
                lineTo(21f, 27f)
                lineTo(21f, 42f)
                lineTo(43f, 42f)
                lineTo(43f, 27f)
                lineTo(42.99f, 26.53f)
                curveTo(42.99f, 25.65f, 42.27f, 25f, 41.39f, 25f)
            }
            path(fill = SolidColor(Color(0xFF012C47))) {
                moveTo(39f, 22.346f)
                lineTo(39f, 25f)
                lineTo(36f, 25f)
                lineTo(36f, 22f)
                curveTo(36f, 19.834f, 34.172f, 18.454f, 32.016f, 18.454f)
                curveTo(29.859f, 18.454f, 28f, 19.834f, 28f, 22f)
                lineTo(28f, 25f)
                lineTo(25f, 25f)
                lineTo(25.01f, 22.346f)
                curveTo(25.01f, 18.496f, 28.166f, 15.372f, 32.016f, 15.372f)
                curveTo(33.941f, 15.372f, 35.687f, 16.151f, 36.949f, 17.413f)
                curveTo(38.211f, 18.675f, 39f, 20.421f, 39f, 22.346f)
            }
            path(fill = SolidColor(Color(0xFF30A1CB))) {
                moveTo(31.999f, 2.727f)
                lineTo(31.999f, 8.96f)
                curveTo(34.485f, 10.343f, 37.381f, 11.904f, 40f, 13f)
                lineTo(40f, 7f)
                curveTo(37.08f, 5.662f, 34.192f, 4.075f, 31.999f, 2.727f)
            }
            path(fill = SolidColor(Color(0xFF30A1CB))) {
                moveTo(57.92f, 17f)
                lineTo(57.92f, 17.41f)
                curveTo(57.9f, 18.8f, 57.84f, 20.32f, 57.72f, 21.95f)
                lineTo(57.72f, 22.02f)
                curveTo(57.68f, 22.55f, 57.63f, 23.09f, 57.58f, 23.65f)
                curveTo(57.04f, 29.12f, 56.54f, 34.36f, 54.04f, 40.12f)
                curveTo(53.99f, 40.24f, 53.94f, 40.35f, 53.89f, 40.47f)
                curveTo(53.63f, 41.04f, 53.35f, 41.62f, 53.06f, 42.21f)
                curveTo(52.54f, 43.22f, 52f, 44.18f, 51.44f, 45.08f)
                curveTo(50.78f, 46.14f, 50.1f, 47.11f, 49.42f, 48.01f)
                curveTo(48.97f, 48.61f, 48.52f, 49.16f, 48.08f, 49.69f)
                curveTo(46.92f, 51.07f, 45.8f, 52.21f, 44.81f, 53.14f)
                curveTo(44.35f, 53.57f, 43.91f, 53.96f, 43.53f, 54.3f)
                curveTo(43.18f, 54.61f, 42.86f, 54.88f, 42.58f, 55.11f)
                curveTo(41.78f, 55.8f, 40.98f, 56.42f, 40.2f, 56.99f)
                curveTo(39.24f, 57.71f, 38.29f, 58.35f, 37.38f, 58.92f)
                curveTo(35.42f, 60.15f, 33.51f, 61.3f, 32f, 61.99f)
                lineTo(32f, 55f)
                lineTo(37f, 55f)
                lineTo(37f, 51f)
                lineTo(40f, 51f)
                lineTo(40f, 45f)
                lineTo(45f, 45f)
                lineTo(45f, 41f)
                lineTo(50f, 41f)
                lineTo(50f, 21f)
                lineTo(45f, 21f)
                lineTo(45f, 17f)
                lineTo(40f, 17f)
                lineTo(40f, 13f)
                lineTo(44f, 13f)
                lineTo(44f, 9f)
                curveTo(45.598f, 9.539f, 47.492f, 10f, 49f, 10.307f)
                lineTo(49f, 17f)
                lineTo(53f, 17f)
                lineTo(53.01f, 11.39f)
                curveTo(54.49f, 11.74f, 55.9f, 12.02f, 57.22f, 12.25f)
                curveTo(57.56f, 12.31f, 57.82f, 12.6f, 57.84f, 12.95f)
                curveTo(57.9f, 14.13f, 57.94f, 15.49f, 57.92f, 17f)
            }
            path(fill = SolidColor(Color(0xFF30A1CB))) {
                moveTo(38f, 44.004f)
                lineToRelative(-5f, 0f)
                lineToRelative(-0f, 4.995f)
                lineToRelative(5f, 0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF012C47))) {
                moveTo(15.005f, 16.291f)
                lineTo(15.002f, 18.996f)
                lineTo(18.999f, 19f)
                lineTo(19f, 15f)
                curveTo(19.606f, 14.718f, 20.469f, 14.274f, 21f, 14f)
                lineTo(21f, 17f)
                lineTo(25f, 17f)
                lineTo(25f, 12f)
                curveTo(27.429f, 10.847f, 29.914f, 9.875f, 31.999f, 8.96f)
                lineTo(31.999f, 2.728f)
                curveTo(28.976f, 4.583f, 25.26f, 6.561f, 20.863f, 8.299f)
                curveTo(15.62f, 10.369f, 10.798f, 11.558f, 6.8f, 12.25f)
                curveTo(6.457f, 12.311f, 6.201f, 12.598f, 6.18f, 12.947f)
                curveTo(6.119f, 14.202f, 6.083f, 15.653f, 6.098f, 17.267f)
                curveTo(6.119f, 19.179f, 6.211f, 21.321f, 6.442f, 23.653f)
                curveTo(7.036f, 29.767f, 7.605f, 35.614f, 10.962f, 42.21f)
                curveTo(14.493f, 49.149f, 19.254f, 53.264f, 21.437f, 55.114f)
                curveTo(25.342f, 58.425f, 29.211f, 60.721f, 31.999f, 61.992f)
                lineTo(31.999f, 55f)
                lineTo(27f, 55f)
                lineTo(27f, 50f)
                lineTo(22f, 50f)
                lineTo(22f, 45f)
                lineTo(19f, 45f)
                lineTo(19f, 39f)
                lineTo(14f, 39f)
                lineTo(14f, 34f)
                lineTo(12f, 34f)
                lineTo(12f, 27f)
                lineTo(16f, 27f)
                lineTo(16f, 22f)
                lineTo(11.981f, 22.006f)
                lineTo(11.988f, 17.119f)
                lineTo(15.005f, 16.291f)
                close()
            }
            path(fill = SolidColor(Color(0xFF012C47))) {
                moveTo(19f, 29f)
                lineToRelative(-5f, 0f)
                lineToRelative(-0f, 5f)
                lineToRelative(5f, 0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF76CDE2))) {
                moveTo(57.92f, 17f)
                lineTo(57.92f, 17.41f)
                curveTo(57.9f, 18.8f, 57.84f, 20.32f, 57.72f, 21.95f)
                lineTo(57.72f, 22f)
                lineTo(53f, 22f)
                lineTo(53f, 17f)
                lineTo(57.92f, 17f)
                close()
            }
            path(fill = SolidColor(Color(0xFF30A1CB))) {
                moveTo(31.999f, 50f)
                lineToRelative(-4.999f, 0f)
                lineToRelative(-0f, 5f)
                lineToRelative(4.999f, 0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF30A1CB))) {
                moveTo(27f, 45f)
                lineToRelative(-5f, 0f)
                lineToRelative(-0f, 5f)
                lineToRelative(5f, 0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF76CDE2))) {
                moveTo(55f, 26f)
                lineToRelative(-5f, 0f)
                lineToRelative(-0f, 5f)
                lineToRelative(5f, 0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF30A1CB))) {
                moveTo(50f, 26f)
                lineToRelative(-5f, 0f)
                lineToRelative(-0f, 5f)
                lineToRelative(5f, 0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF76CDE2))) {
                moveTo(50f, 36f)
                lineToRelative(-5f, 0f)
                lineToRelative(-0f, 5f)
                lineToRelative(5f, 0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF30A1CB))) {
                moveTo(21f, 37f)
                horizontalLineToRelative(5f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(-5f)
                close()
            }
            path(fill = SolidColor(Color(0xFF30A1CB))) {
                moveTo(38f, 31f)
                horizontalLineToRelative(5f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(-5f)
                close()
            }
        }.build()

        return _AppIcon!!
    }

@Suppress("ObjectPropertyName")
private var _AppIcon: ImageVector? = null
