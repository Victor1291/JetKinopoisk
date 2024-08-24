package com.example.filter

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.shu.models.Countries
import com.shu.models.Genres

@Composable
fun DialogListView(
    viewModel: FilterViewModel,
    isCountries: Boolean = true,
    onDismissRequest: () -> Unit,
    onConfirmation: (Countries) -> Unit,
) {
    val filter = viewModel.filter.collectAsState()
    val city by viewModel.country.collectAsState()
    LaunchedEffect(true) {
        viewModel.updateSearchTextState("")
        viewModel.refreshCountry()
    }

    val (selectedOption, onOptionSelected) = remember {
        if (isCountries) {
            mutableStateOf(Countries(0, "США"))
        } else {
            mutableStateOf(
                Genres(0, "триллер")
            )
        }
    }
    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            CountryDialogView2(
                onDismissRequest = {
                    onDismissRequest()
                },
                onConfirmation = {
                    viewModel.updateSearchTextState(it.country)
                    viewModel.setFilter(
                        filter.value.copy(
                            country = it.id,
                            countryName = it.country
                        )
                    )
                    onDismissRequest()
                },
                viewModel = viewModel,
                //TODO запоминать выбранный вариант
                countrySelected = Countries(0, "США"),
                onDismiss = {
                    onDismissRequest()
                }
            )
        }
    }
}