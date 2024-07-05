package com.example.design_system.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.JetCinemaTheme

@Composable
fun ClearItemCard(
    type: String,
    onClearClick: (String?) -> Unit,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(4.dp)
            .width(130.dp)
            .height(220.dp)
            .clickable { },

        ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.Center),
                onClick = { onClearClick(type) },
            ) {
                Text(
                    stringResource(id = R.string.clear),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClearPreview() {
    JetCinemaTheme {

        ClearItemCard(
            "1",
            onClearClick = {}
        )

    }
}