package com.example.challengemock.domain.form.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ValidateFormUseCase @Inject constructor(
) {

    suspend operator fun invoke(params: Params): List<ValidationError> = withContext(Dispatchers.Default) {

        val errors = mutableListOf<ValidationError>()

        validateUserName(params.userName)?.let {
            errors.add(it)
        }

        validateEmail(params.email)?.let {
            errors.add(it)
        }

        validateNumbers(params.numbers)?.let {
            errors.add(it)
        }

        validatePromoCode(params.promoCode)?.let {
            errors.add(it)
        }

        validateDeliverDate(params.deliverDate)?.let {
            errors.add(it)
        }

        validateClassification(params.classification)?.let {
            errors.add(it)
        }

        return@withContext errors
    }

    private fun validateClassification(classification: String) : ValidationError? {
        if (classification.isBlank()) return ValidationError.CLASSIFICATION_EMPTY
        return null
    }

    private fun validateDeliverDate(deliverDate: String) : ValidationError? {
        if (deliverDate.isBlank()) return ValidationError.DATE_EMPTY

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = sdf.parse(deliverDate)
        val currentDate = Date()

        if (date == null) return ValidationError.DATE_EMPTY
        if (date.after(currentDate)) return ValidationError.DATE_IS_FUTURE

        val calendar = Calendar.getInstance()
        calendar.time = date

        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) return ValidationError.DATE_IS_MONDAY

        return null
    }

    private fun validatePromoCode(promoCode: String) : ValidationError? {
        if (promoCode.isBlank()) return ValidationError.PROMOCODE_EMPTY
        if (promoCode.length < 3 || promoCode.length > 7) return ValidationError.PROMOCODE_LENGHT

        val regex = "[A-Z\\-]+".toRegex()
        if (regex.matches(promoCode)) return null

        return ValidationError.PROMOCODE_INVALID
    }

    private fun validateNumbers(numbers: String) : ValidationError? {
        if (numbers.isBlank()) return ValidationError.NUMBERS_EMPTY

        val regex = "^[0-9]*$".toRegex()
        if (regex.matches(numbers)) return null

        return ValidationError.NUMBERS_INVALID
    }

    private fun validateEmail(email: String): ValidationError? {
        if (email.isBlank()) return ValidationError.EMAIL_EMPTY
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) return null

        return ValidationError.EMAIL_INVALID
    }

    private fun validateUserName(userName: String): ValidationError? {
        if (userName.isNotBlank()) return null

        return ValidationError.USERNAME_EMPTY
    }

    enum class ValidationError {
        USERNAME_EMPTY,
        EMAIL_EMPTY,
        EMAIL_INVALID,
        NUMBERS_EMPTY,
        NUMBERS_INVALID,
        PROMOCODE_EMPTY,
        PROMOCODE_INVALID,
        PROMOCODE_LENGHT,
        DATE_EMPTY,
        DATE_IS_FUTURE,
        DATE_IS_MONDAY,
        CLASSIFICATION_EMPTY,
    }

    data class Params(
        val userName: String,
        val email: String,
        val numbers: String,
        val promoCode: String,
        val deliverDate: String,
        val classification: String,
    )
}