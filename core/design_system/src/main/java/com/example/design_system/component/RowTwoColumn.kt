package com.example.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.design_system.theme.JetCinemaTheme

@Composable
fun RowTwoColumn(
    rowId: Int,
    rating: String,
    year: String,
    first: String,
    second: String,
    onClick: (Int?) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
            .clickable { onClick(rowId) }
            .background(Color.Blue.copy(alpha = 0.2f)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,

        ) {

        Column(
            modifier = Modifier.padding(start = 8.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (rating.isNotEmpty()) {
                Text(
                    text = rating,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            if (year.isNotEmpty()) {
                Text(
                    text = year,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start
        ) {

            if (first.isNotEmpty()) {
                Text(
                    text = first,
                    modifier = Modifier.padding(start = 8.dp),
                    fontSize = 18.sp,
                    maxLines = 1,
                    lineHeight = 19.sp,
                    color = MaterialTheme.colorScheme.primary,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            if (second.isNotEmpty()) {
                Text(
                    text = second,
                    modifier = Modifier.padding(start = 8.dp),
                    lineHeight = 19.sp,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                )
            }

        }

    }
}


@Preview(showBackground = true)
@Composable
fun RowTwoColumnPreview() {
    JetCinemaTheme {
        val countriesList = "Russia, Belarus, China, India "
        val length = "2h30"
        val rate = "18"
        val second = if (countriesList.length > 30)  "${countriesList.take(20)}. $length $rate+"
        else "$countriesList $length $rate+"
        RowTwoColumn(
            rowId = 1,
            rating = "7.7",
            year = "2024",
            first = "Comedy,action,detective",
            second = second,
            onClick = {}
        )
    }
}
