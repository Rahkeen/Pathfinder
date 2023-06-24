package dev.supergooey.pathfinder

import android.graphics.PathMeasure
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import dev.supergooey.pathfinder.ui.theme.PathfinderTheme
import dev.supergooey.pathfinder.ui.theme.RainbowGradient
import kotlin.math.ceil
import kotlin.math.floor

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      Examples()
    }
  }
}

@Preview
@Composable
fun Examples() {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = Color.White),
    verticalArrangement = Arrangement.spacedBy(64.dp, Alignment.CenterVertically),
  ) {
    PathGradientSimple()
    PathGradientCool()
  }
}

@Preview
@Composable
fun PathGradientSimple() {
  PathfinderTheme {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.White),
      contentAlignment = Alignment.Center
    ) {
      Canvas(
        modifier = Modifier
          .width(260.dp)
          .height(120.dp)
      ) {
        val path = Path()
        PathParser()
          .parsePathString(
            "M13.63 248.31C13.63 248.31 51.84 206.67 84.21 169.31C140.84 103.97 202.79 27.66 150.14 14.88C131.01 10.23 116.36 29.88 107.26 45.33C69.7 108.92 58.03 214.33 57.54 302.57C67.75 271.83 104.43 190.85 140.18 193.08C181.47 195.65 145.26 257.57 154.53 284.39C168.85 322.18 208.22 292.83 229.98 277.45C265.92 252.03 288.98 231.22 288.98 200.45C288.98 161.55 235.29 174.02 223.3 205.14C213.93 229.44 214.3 265.89 229.3 284.14C247.49 306.28 287.67 309.93 312.18 288.46C337 266.71 354.66 234.56 368.68 213.03C403.92 158.87 464.36 86.15 449.06 30.03C446.98 22.4 440.36 16.57 432.46 16.26C393.62 14.75 381.84 99.18 375.35 129.31C368.78 159.83 345.17 261.31 373.11 293.06C404.43 328.58 446.29 262.4 464.66 231.67C468.66 225.31 472.59 218.43 476.08 213.07C511.33 158.91 571.77 86.19 556.46 30.07C554.39 22.44 547.77 16.61 539.87 16.3C501.03 14.79 489.25 99.22 482.76 129.35C476.18 159.87 452.58 261.35 480.52 293.1C511.83 328.62 562.4 265.53 572.64 232.86C587.34 185.92 620.94 171.58 660.91 180.29C616 166.66 580.86 199.67 572.64 233.16C566.81 256.93 573.52 282.16 599.25 295.77C668.54 332.41 742.8 211.69 660.91 180.29C643.67 181.89 636.15 204.77 643.29 227.78C654.29 263.97 704.29 268.27 733.08 256"
          )
          .toPath(path)

        drawPath(
          path = path,
          brush = Brush.horizontalGradient(RainbowGradient),
          style = Stroke(
            width = 30f,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round,
          )
        )
      }
    }
  }
}

@Preview
@Composable
fun PathGradientCool() {
  PathfinderTheme {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.White),
      contentAlignment = Alignment.Center
    ) {
      val transition = rememberInfiniteTransition("path")
      val pathMultiplier by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
          animation = tween(durationMillis = 4000, easing = EaseInOut),
          repeatMode = RepeatMode.Reverse
        ),
        label = "path progress"
      )

      Canvas(
        modifier = Modifier
          .width(260.dp)
          .height(120.dp)
      ) {
        val path = Path()
        PathParser()
          .parsePathString(
            "M13.63 248.31C13.63 248.31 51.84 206.67 84.21 169.31C140.84 103.97 202.79 27.66 150.14 14.88C131.01 10.23 116.36 29.88 107.26 45.33C69.7 108.92 58.03 214.33 57.54 302.57C67.75 271.83 104.43 190.85 140.18 193.08C181.47 195.65 145.26 257.57 154.53 284.39C168.85 322.18 208.22 292.83 229.98 277.45C265.92 252.03 288.98 231.22 288.98 200.45C288.98 161.55 235.29 174.02 223.3 205.14C213.93 229.44 214.3 265.89 229.3 284.14C247.49 306.28 287.67 309.93 312.18 288.46C337 266.71 354.66 234.56 368.68 213.03C403.92 158.87 464.36 86.15 449.06 30.03C446.98 22.4 440.36 16.57 432.46 16.26C393.62 14.75 381.84 99.18 375.35 129.31C368.78 159.83 345.17 261.31 373.11 293.06C404.43 328.58 446.29 262.4 464.66 231.67C468.66 225.31 472.59 218.43 476.08 213.07C511.33 158.91 571.77 86.19 556.46 30.07C554.39 22.44 547.77 16.61 539.87 16.3C501.03 14.79 489.25 99.22 482.76 129.35C476.18 159.87 452.58 261.35 480.52 293.1C511.83 328.62 562.4 265.53 572.64 232.86C587.34 185.92 620.94 171.58 660.91 180.29C616 166.66 580.86 199.67 572.64 233.16C566.81 256.93 573.52 282.16 599.25 295.77C668.54 332.41 742.8 211.69 660.91 180.29C643.67 181.89 636.15 204.77 643.29 227.78C654.29 263.97 704.29 268.27 733.08 256"
          )
          .toPath(path)

        val measure = PathMeasure().apply {
          setPath(path.asAndroidPath(), false)
        }

        val length = measure.length
        val segmentLength = 5

        val position = FloatArray(2)
        val tangent = FloatArray(2)
        var currSegment = 0f
        val colors = RainbowGradient

        // draw points
        while (currSegment < length * pathMultiplier) {
          measure.getPosTan(currSegment, position, tangent)
          val x = position[0]
          val y = position[1]

          // calculate which colors to use
          val percent = currSegment / length
          val progress = colors.lastIndex * percent
          val color1 = colors[floor(progress).toInt()]
          val color2 = colors[ceil(progress).toInt()]
          val mix = progress - floor(progress)
          val lerpColor = lerp(color1, color2, mix)
          drawCircle(
            color = lerpColor, // change this color
            center = Offset(x, y),
            radius = 15f
          )

          currSegment += segmentLength
        }
      }
    }
  }
}

@Preview
@Composable
fun PathGradientDiscrete() {
  PathfinderTheme {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.White),
      contentAlignment = Alignment.Center
    ) {
      Canvas(
        modifier = Modifier
          .width(260.dp)
          .height(120.dp)
      ) {
        val path = Path()
        PathParser()
          .parsePathString(
            "M13.63 248.31C13.63 248.31 51.84 206.67 84.21 169.31C140.84 103.97 202.79 27.66 150.14 14.88C131.01 10.23 116.36 29.88 107.26 45.33C69.7 108.92 58.03 214.33 57.54 302.57C67.75 271.83 104.43 190.85 140.18 193.08C181.47 195.65 145.26 257.57 154.53 284.39C168.85 322.18 208.22 292.83 229.98 277.45C265.92 252.03 288.98 231.22 288.98 200.45C288.98 161.55 235.29 174.02 223.3 205.14C213.93 229.44 214.3 265.89 229.3 284.14C247.49 306.28 287.67 309.93 312.18 288.46C337 266.71 354.66 234.56 368.68 213.03C403.92 158.87 464.36 86.15 449.06 30.03C446.98 22.4 440.36 16.57 432.46 16.26C393.62 14.75 381.84 99.18 375.35 129.31C368.78 159.83 345.17 261.31 373.11 293.06C404.43 328.58 446.29 262.4 464.66 231.67C468.66 225.31 472.59 218.43 476.08 213.07C511.33 158.91 571.77 86.19 556.46 30.07C554.39 22.44 547.77 16.61 539.87 16.3C501.03 14.79 489.25 99.22 482.76 129.35C476.18 159.87 452.58 261.35 480.52 293.1C511.83 328.62 562.4 265.53 572.64 232.86C587.34 185.92 620.94 171.58 660.91 180.29C616 166.66 580.86 199.67 572.64 233.16C566.81 256.93 573.52 282.16 599.25 295.77C668.54 332.41 742.8 211.69 660.91 180.29C643.67 181.89 636.15 204.77 643.29 227.78C654.29 263.97 704.29 268.27 733.08 256"
          )
          .toPath(path)

        val measure = PathMeasure().apply {
          setPath(path.asAndroidPath(), false)
        }

        val length = measure.length
        val segmentLength = 5

        val position = FloatArray(2)
        val tangent = FloatArray(2)
        var currSegment = 0f
        val colors = RainbowGradient

        // draw points
        while (currSegment < length) {
          measure.getPosTan(currSegment, position, tangent)
          val x = position[0]
          val y = position[1]

          // calculate which colors to use
          val percent = currSegment / length
          val index = (colors.size * percent).toInt()
          val color = colors[index]

          drawCircle(
            color = color, // change this color
            center = Offset(x, y),
            radius = 15f
          )

          currSegment += segmentLength
        }
      }
    }
  }
}
