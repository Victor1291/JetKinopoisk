package com.example.design_system.component

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import kotlin.math.abs

/**
 * При смене конфигурации сделать другой лист горизонтальный.
 * последний год в таблице должен быть текущим.
 * не увеличивать больше текущего года.
 */

/*
Это логика рисования, так что Ок
 */

@Composable
fun StartImage(
    maxItemsInEachRow: Int,
    list: List<String>,
    onSelect1: (Int) -> Unit,
) {
    var isAnimated by remember { mutableStateOf(true) }
    // При старте анимация срабатывает. выбраная дата отмечается плавно

    val color = remember { Animatable(Color.White) }

    LaunchedEffect(isAnimated) {
        color.snapTo(Color.Blue.copy(alpha = 0.7f))
        color.animateTo(Color.Black, animationSpec = tween(2000))
    }
    Column(
        Modifier
            .background(Color.Blue.copy(alpha = 0.7f))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        ExcellImage(maxItemsInEachRow = maxItemsInEachRow, sizeList = list.size) {
            list.forEachIndexed { index, item ->

                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .padding(4.dp)
                        .border(BorderStroke(2.dp, Color.White))
                        .clickable { },
                ) {

                    Text(
                        modifier = Modifier
                            .padding(4.dp)
                            .background(Color.Transparent.copy(alpha = 0.3f))
                            .align(Alignment.Center)
                            .clickable {
                                isAnimated = !isAnimated
                                onSelect1(index)
                            },
                        text = item, color = Color.White.copy(alpha = 0.6F), fontSize = 12.sp,
                    )

                }
            }

        }
    }
}


@Preview
@Composable
fun NewImageFlow() {
    //Первая таблица
    val year = 1..23
    val list = mutableListOf<String>()
    year.forEach {
        list.add(it.toString())
    }
    val delitel = list.size / 6.0
    val ostatok = list.size % 6
    val heightAbs = if (ostatok == 0) (delitel).toInt() else (delitel + 1).toInt()
    val heightMax = abs(heightAbs * 120)
    list[0] = heightAbs.toString()
    list[1] = delitel.toString()
    list[2] = ostatok.toString()
    StartImage(
        maxItemsInEachRow = 6,
        list = list,
        onSelect1 = {

        },
    )
}

//Нужно вычислить высоту колонок . Как узнать сколько рядов ? 13
//6  13 / 6
@Composable
fun ExcellImage(
    maxItemsInEachRow: Int,
    sizeList: Int,
    modifier: Modifier = Modifier, content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier, content = content
    ) { measurables, constraints ->

        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        val delitel = sizeList / maxItemsInEachRow.toDouble()
        val ostatok = sizeList % maxItemsInEachRow
        val heightAbs = if (ostatok == 0) (delitel).toInt() else (delitel + 1).toInt()
        val heightMax = (heightAbs * 120)
        val widthMAx = maxItemsInEachRow * 120
        layout(
            constraints.constrainHeight(widthMAx),
            constraints.constrainHeight(if (heightMax > 1000) 1000 else heightMax)
        ) {
            var x = 20
            var column = maxItemsInEachRow - 1
            var y = 20
            placeables.fastForEachIndexed { index, placeable ->
                placeable.placeRelative(x = x, y = y)

                if (index == column) {
                    x = 20
                    y += placeable.height
                    column += (maxItemsInEachRow)
                } else {
                    x += placeable.width
                }
            }
        }
    }
}

@Preview
@Composable
fun NewSampleOne() {

    SampleOne(modifier = Modifier) {
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .padding(4.dp)
                .border(BorderStroke(2.dp, Color.White))
                .clickable { },
        ) {

            Text(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .background(Color.Transparent.copy(alpha = 0.3f))
                    .align(Alignment.TopCenter)
                    .clickable {
                    },
                text = "item", color = Color.White.copy(alpha = 0.6F), fontSize = 12.sp,
            )

            Text(
                modifier = Modifier
                    .padding(bottom =  6.dp)
                    .background(Color.Transparent.copy(alpha = 0.3f))
                    .align(Alignment.BottomCenter)
                    .clickable {
                    },
                text = "10", color = Color.White.copy(alpha = 0.6F), fontSize = 12.sp,
            )

        }

    }
}


@Composable
fun SampleOne(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    // We build a layout that will occupy twice as much space as its children,Мы создаем макет, который будет занимать в два раза больше места, чем его дочерние элементы
    // and will position them to be bottom right aligned  и расположит их так, чтобы они были выровнены по правому нижнему краю
    Layout(content = content) { measurables, constraints ->
        // measurables contains one element corresponding to each of our layout children.
        // constraints are the constraints that our parent is currently measuring us with.
        val childConstraints = Constraints(
            minWidth = constraints.minWidth / 2,
            minHeight = constraints.minHeight / 2,
            maxWidth = if (constraints.hasBoundedWidth) {
                constraints.maxWidth / 2
            } else {
                Constraints.Infinity
            },
            maxHeight = if (constraints.hasBoundedHeight) {
                constraints.maxHeight / 2
            } else {
                Constraints.Infinity
            }
        )

        // We measure the children with half our constraints, to ensure we can be double
        // the size of the children.
        val placeables = measurables.map { it.measure(childConstraints) }
        val layoutWidth = (placeables.maxByOrNull { it.width }?.width ?: 0) * 2
        val layoutHeight = (placeables.maxByOrNull { it.height }?.height ?: 0) * 2
        // We call layout to set the size of the current layout and to provide the positioning
        // of the children. The children are placed relative to the current layout place.


        layout(layoutWidth, layoutHeight) {
            placeables.forEach {
                it.placeRelative(layoutWidth - it.width, layoutHeight - it.height)
            }
        }
    }

}

@Preview
@Composable
fun NewSampleTwo() {

    SampleTwo(modifier = Modifier
        .width(300.dp)
        .height(300.dp)) {
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .padding(4.dp)
                .border(BorderStroke(2.dp, Color.White))
                .clickable { },
        ) {

            Text(
                modifier = Modifier
                    .padding(4.dp)
                    .background(Color.Transparent.copy(alpha = 0.3f))
                    .align(Alignment.Center)
                    .clickable {
                    },
                text = "10", color = Color.White.copy(alpha = 0.6F), fontSize = 12.sp,
            )

        }

    }
}


@Composable
fun SampleTwo(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // We build a layout that will occupy twice as much space as its children,
    // Мы создаем макет, который будет занимать в два раза больше места, чем его дочерние элементы,
    // and will position them to be bottom right aligned.
    val measurePolicy = object : MeasurePolicy {
        override fun MeasureScope.measure(
            measurables: List<Measurable>,
            constraints: Constraints
        ): MeasureResult {
            // measurables contains one element corresponding to each of our layout children.
            // constraints are the constraints that our parent is currently measuring us with.
            val childConstraints = Constraints(
                minWidth = constraints.minWidth / 2,
                minHeight = constraints.minHeight / 2,
                maxWidth = if (constraints.hasBoundedWidth) {
                    constraints.maxWidth / 2
                } else {
                    Constraints.Infinity
                },
                maxHeight = if (constraints.hasBoundedHeight) {
                    constraints.maxHeight / 2
                } else {
                    Constraints.Infinity
                }
            )
            // We measure the children with half our constraints, to ensure we can be double
            // the size of the children.
            val placeables = measurables.map { it.measure(childConstraints) }
            val layoutWidth = (placeables.maxByOrNull { it.width }?.width ?: 0) * 2
            val layoutHeight = (placeables.maxByOrNull { it.height }?.height ?: 0) * 2
            // We call layout to set the size of the current layout and to provide the positioning
            // of the children. The children are placed relative to the current layout place.

            //внёс изменения умножил на 2 , чтоб рисовало слева
            return layout(layoutWidth, layoutHeight) {
                placeables.forEach {
                    it.placeRelative(layoutWidth - it.width * 2, layoutHeight - it.height)
                }
            }
        }

        // The min intrinsic width of this layout will be twice the largest min intrinsic
        //Минимальная внутренняя ширина этого макета будет в два раза больше минимальной внутренней
        // width of a child. Note that we call minIntrinsicWidth with h / 2 for children,
        //ширина дочернего элемента. Обратите внимание, что для дочерних элементов мы вызываем minIntrinsicWidth с h / 2,
        // since we should be double the size of the children.
        override fun IntrinsicMeasureScope.minIntrinsicWidth(
            measurables: List<IntrinsicMeasurable>,
            height: Int
        ) = (
                measurables.map {
                    it.minIntrinsicWidth(height / 2)
                }.maxByOrNull { it } ?: 0) * 2

        override fun IntrinsicMeasureScope.minIntrinsicHeight(
            measurables: List<IntrinsicMeasurable>,
            width: Int
        ) = (measurables.map { it.minIntrinsicHeight(width / 2) }.maxByOrNull { it } ?: 0) * 2

        override fun IntrinsicMeasureScope.maxIntrinsicWidth(
            measurables: List<IntrinsicMeasurable>,
            height: Int
        ) = (measurables.map { it.maxIntrinsicHeight(height / 2) }.maxByOrNull { it } ?: 0) * 2

        override fun IntrinsicMeasureScope.maxIntrinsicHeight(
            measurables: List<IntrinsicMeasurable>,
            width: Int
        ) = (measurables.map { it.maxIntrinsicHeight(width / 2) }.maxByOrNull { it } ?: 0) * 2
    }
    Layout(content = content, measurePolicy = measurePolicy)


}
