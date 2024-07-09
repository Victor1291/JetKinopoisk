package com.example.design_system.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.JetCinemaTheme

@Composable
fun NewRowTabs(
    modifier: Modifier,
    selected: Int,
    countList: List<Int>,
    list: List<String>,
    onClick: (Int) -> Unit
) {

    LazyRow(
        contentPadding = PaddingValues(4.dp),
        modifier = modifier,
    ) {

        items(countList.size) { category ->

            if (countList[category] != 0) {

                InputChip(
                    onClick = {
                        onClick(category)
                    },
                    label = { Text(text = "${list[category]} ${countList[category]} ") },
                    selected = category == selected,
                    colors = InputChipDefaults.inputChipColors(
                        containerColor = Color.Blue.copy(alpha = 0.4f),
                        labelColor = Color.White,
                        selectedContainerColor = Color.Red.copy(alpha = 0.4f),
                        selectedLabelColor = Color.White,
                    )
                )
                /*OutlinedButton(
                    onClick = {
                        onClick(category)
                    },
                    enabled = category == selected,
                    content = {
                        Text(
                            text = "${list[category]} ${countList[category]} ",
                        )
                    },
                )*/
            }
        }
    }
}

@ThemePreviews
@Composable
fun NewRowTabsPreview() {
    JetCinemaTheme {
        var selected by remember {
            mutableStateOf(0)
        }
        val titles = listOf("Topics", "People", "STILL", "DOOR", "FINISH")
        NewRowTabs(
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