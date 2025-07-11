package com.example.challengemock.feature.form

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.challengemock.feature.form.components.DatePickerFieldToModal
import com.example.challengemock.feature.form.components.DropDown
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FormScreenRoot(viewModel: FormViewModel = hiltViewModel(),) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest {
            when (it) {
                is FormContract.Effect.ShowSuccessToast -> {
                    Toast.makeText(context, "Form is valid", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    FormScreen(
        state = state,
        onEvent = { viewModel.event(it) }
    )
}

@Composable
private fun FormScreen(
    state: FormContract.State,
    onEvent: (event: FormContract.Event) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            TextField(
                value = state.userName.value,
                onValueChange = { onEvent(
                    FormContract.Event.OnUpdateUserName(it)
                ) },
                label = { Text("Nome do utilizador") },
                isError = state.userName.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )

            TextField(
                value = state.email.value,
                onValueChange = { onEvent(
                    FormContract.Event.OnUpdateEmail(it)
                ) },
                label = { Text("Email") },
                isError = state.email.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )

            TextField(
                value = state.numbers.value,
                onValueChange = { onEvent(
                    FormContract.Event.OnUpdateNumber(it)
                ) },
                label = { Text("Número") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.numbers.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )

            TextField(
                value = state.promoCode.value,
                onValueChange = { onEvent(
                    FormContract.Event.OnUpdatePromoCode(it)
                ) },
                label = { Text("Código promocional") },
                isError = state.promoCode.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )

            DatePickerFieldToModal(
                state = state,
                onDateChanged = { onEvent(
                    FormContract.Event.OnUpdateDeliverDate(it)
                ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )

            DropDown(
                state = state,
                onClassificationChanged = { onEvent(
                    FormContract.Event.OnUpdateDeliverClassification(it)
                ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )
        }

        Button(
            onClick = { onEvent(FormContract.Event.OnSubmit) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Submit")
        }
    }
}
