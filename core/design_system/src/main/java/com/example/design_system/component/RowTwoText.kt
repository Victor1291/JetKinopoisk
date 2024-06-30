package com.example.design_system.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.design_system.theme.JetCinemaTheme

@Composable
fun RowTwoText(
    first: String = "",
    second: String = "",
    size: TextUnit = 16.sp,
    onClick: () -> Unit = {},
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = first,
            fontSize = size,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = second,
            color = MaterialTheme.colorScheme.primary,
            fontSize = size,
            modifier = Modifier
                .clickable { onClick() },
        )

    }
}

@Preview(showBackground = true)
@Composable
fun RowTwoTextPreview() {
    JetCinemaTheme {
        RowTwoText ("First","Two")
    }
}