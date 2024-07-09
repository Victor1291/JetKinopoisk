package com.example.design_system.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.JetCinemaTheme


@Composable
fun NewTabs(
    modifier: Modifier,
    selected: Int,
    countList: List<Int>,
    list: List<String>,
    onClick: (Int) -> Unit
) {

    TabRow(
        selectedTabIndex = selected,
        modifier = modifier.padding(top = 20.dp),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onSurface,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selected]),
                height = 2.dp,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        tabs = {

            countList.forEachIndexed { index, count ->
                if (count != 0) {
                    Tab(
                        selected = selected == index,
                        onClick = { onClick(index) },
                        modifier = modifier,
                        enabled = true,
                        text = {
                            val style =
                                MaterialTheme.typography.labelLarge.copy(textAlign = TextAlign.Center)
                            ProvideTextStyle(
                                value = style,
                                content = {
                                    Box(modifier = Modifier.padding(top = 7.dp)) {
                                        Text(text = "${list[index]} ${countList[index]}")
                                    }
                                },
                            )
                        },
                    )
                }
            }
        },
    )
}

@ThemePreviews
@Composable
fun TabsNewPreview() {
    JetCinemaTheme {
        var selected by remember {
            mutableStateOf(0)
        }
        val titles = listOf("Topics", "People", "STILL", "DOOR", "FINISH")
        NewTabs(
            modifier = Modifier,
            selected = selected,
            countList = listOf(10, 3, 15, 32, 1),
            list = titles,
            onClick = {
                selected = it
            },
        )
    }
}