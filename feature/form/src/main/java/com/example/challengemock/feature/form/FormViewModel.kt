package com.example.challengemock.feature.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengemock.domain.form.usecase.ValidateFormUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    val validateFormUseCase: ValidateFormUseCase,
): ViewModel(), FormContract {

    private val mutableUIState: MutableStateFlow<FormContract.State> =
        MutableStateFlow(FormContract.State())

    private val mutableSharedFlow: MutableSharedFlow<FormContract.Effect> =
        MutableSharedFlow()

    override val state = mutableUIState.onStart {

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = mutableUIState.value
    )

    override val effect = mutableSharedFlow.asSharedFlow()

    override fun event(event: FormContract.Event) {
        when (event) {
            is FormContract.Event.OnSubmit -> onSubmit()
            is FormContract.Event.OnUpdateUserName -> onUpdateUserName(event.userName)
            is FormContract.Event.OnUpdateEmail -> onUpdateEmail(event.email)
            is FormContract.Event.OnUpdateNumber -> onUpdateNumber(event.number)
            is FormContract.Event.OnUpdatePromoCode -> onUpdatePromoCode(event.promoCode)
            is FormContract.Event.OnUpdateDeliverDate -> onUpdateDeliverDate(event.timeInMillis)
            is FormContract.Event.OnUpdateDeliverClassification -> onUpdateClassification(event.classification)
        }
    }

    private fun onSubmit() {
        viewModelScope.launch {
            val errors = state.value.let {
                validateFormUseCase(ValidateFormUseCase.Params(
                    userName = it.userName.value,
                    email = it.email.value,
                    numbers = it.numbers.value,
                    promoCode = it.promoCode.value,
                    deliverDate = it.deliverDate.value,
                    classification = it.classification.value,
                ))
            }

            mutableUIState.update { state ->
                state.copy(
                    userName = state.userName.copy(
                        error = errors.contains(ValidateFormUseCase.ValidationError.USERNAME_EMPTY)
                    ),
                    email = state.email.copy(
                        error = errors.contains(ValidateFormUseCase.ValidationError.EMAIL_EMPTY) ||
                                errors.contains(ValidateFormUseCase.ValidationError.EMAIL_INVALID)
                    ),
                    numbers = state.numbers.copy(
                        error = errors.contains(ValidateFormUseCase.ValidationError.NUMBERS_EMPTY) ||
                                errors.contains(ValidateFormUseCase.ValidationError.NUMBERS_INVALID)
                    ),
                    promoCode = state.promoCode.copy(
                        error = errors.contains(ValidateFormUseCase.ValidationError.PROMOCODE_EMPTY) ||
                                errors.contains(ValidateFormUseCase.ValidationError.PROMOCODE_INVALID) ||
                                errors.contains(ValidateFormUseCase.ValidationError.PROMOCODE_LENGHT)
                    ),
                    deliverDate = state.deliverDate.copy(
                        error = errors.contains(ValidateFormUseCase.ValidationError.DATE_EMPTY) ||
                                errors.contains(ValidateFormUseCase.ValidationError.DATE_IS_FUTURE) ||
                                errors.contains(ValidateFormUseCase.ValidationError.DATE_IS_MONDAY)
                    ),
                    classification = state.classification.copy(
                        error = errors.contains(ValidateFormUseCase.ValidationError.CLASSIFICATION_EMPTY)
                    ),
                )
            }

            if (errors.isEmpty()) {
                mutableSharedFlow.emit(FormContract.Effect.ShowSuccessToast)
            }
        }
    }

    private fun onUpdateUserName(userName: String) {
        mutableUIState.update { state ->
            state.copy(
                userName = state.userName.updateValue(userName)
            )
        }
    }

    private fun onUpdateEmail(email: String) {
        mutableUIState.update { state ->
            state.copy(
                email = state.email.updateValue(email)
            )
        }
    }

    private fun onUpdateNumber(number: String) {
        mutableUIState.update { state ->
            state.copy(
                numbers = state.numbers.updateValue(number)
            )
        }
    }

    private fun onUpdatePromoCode(promoCode: String) {
        mutableUIState.update { state ->
            state.copy(
                promoCode = state.promoCode.updateValue(promoCode)
            )
        }
    }

    private fun onUpdateDeliverDate(timeInMillis: Long) {
        viewModelScope.launch {
            val pattern = "dd/MM/yyyy"
            val date = Date(timeInMillis)

            val formatter = SimpleDateFormat(pattern, Locale("pt-pt")).apply {
                timeZone = TimeZone.getTimeZone("GMT")
            }

            mutableUIState.update { state ->
                state.copy(
                    deliverDate = state.deliverDate.updateValue(formatter.format(date)),
                )
            }
        }
    }

    private fun onUpdateClassification(classification: String) {
        mutableUIState.update { state ->
            state.copy(
                classification = state.classification.updateValue(classification),
            )
        }
    }

}