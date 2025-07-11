package com.example.challengemock.feature.form

import com.example.challengemock.common.error.Failure
import com.example.challengemock.feature.form.util.ValidField
import com.example.challengemock.ui.core.mvi.MVIContract

interface FormContract: MVIContract<
        FormContract.State,
        FormContract.Effect,
        FormContract.Event,
> {
    sealed class  Event {
        data object OnSubmit: Event()
        data class OnUpdateUserName(val userName: String): Event()
        data class OnUpdateEmail(val email: String): Event()
        data class OnUpdateNumber(val number: String): Event()
        data class OnUpdatePromoCode(val promoCode: String): Event()
        data class OnUpdateDeliverDate(val timeInMillis: Long): Event()
        data class OnUpdateDeliverClassification(val classification: String): Event()
    }

    data class State(
        val userName: ValidField<String> = ValidField(value = ""),
        val email: ValidField<String> = ValidField(value = ""),
        val numbers: ValidField<String> = ValidField(value = ""),
        val promoCode: ValidField<String> = ValidField(value = ""),
        val deliverDate: ValidField<String> = ValidField(value = ""),
        val classification: ValidField<String> = ValidField(value = ""),

        val isLoading: Boolean = true,
        val error: Failure? = null,

        val classifications: List<String> = listOf(
            "Mau",
            "Satisfatório",
            "Bom",
            "Muito Bom",
            "Excelente",
        ),
    )

    sealed class Effect {
        data object ShowSuccessToast: Effect()
    }
}