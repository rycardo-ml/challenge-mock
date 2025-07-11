package com.example.challengemock.feature.form.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.challengemock.feature.form.FormContract

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(
    state: FormContract.State,
    onClassificationChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it },
        modifier = modifier
    ) {
        TextField(
            value = state.classification.value,
            label = { Text("Classificação") },
            onValueChange = { },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            isError = state.classification.error,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
        ) {
            state.classifications.forEach {
                DropdownMenuItem(
                    text = {
                        Text(it)
                    },
                    onClick = {
                        onClassificationChanged(it)
                        isExpanded = false
                    }
                )
            }
        }
    }
}