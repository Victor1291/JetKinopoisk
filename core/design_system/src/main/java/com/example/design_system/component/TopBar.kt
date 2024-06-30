package com.example.design_system.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TopBar(
    header: String,
    rightIconImageVector: ImageVector? = null,
    leftIconImageVector: ImageVector? = null,
    onLeftIconClick: (() -> Unit)? = null,
    onRightIconClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        leftIconImageVector?.let {
            Icon(
                imageVector = it,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "back",
                modifier = Modifier.clickable {
                    if (onLeftIconClick != null) {
                        onLeftIconClick()
                    }
                }
            )
        }

        Text(
            text = header,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        )
        rightIconImageVector?.let {
            Icon(
                imageVector = it,
                contentDescription = "right",
                modifier = Modifier.clickable {
                    if (onRightIconClick != null) {
                        onRightIconClick()
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopBar() {
    TopBar(
        header = "Title",
        leftIconImageVector = Icons.Rounded.Search,
        rightIconImageVector = Icons.Rounded.Settings,
        onLeftIconClick = {},
        onRightIconClick = {}
    )
}
