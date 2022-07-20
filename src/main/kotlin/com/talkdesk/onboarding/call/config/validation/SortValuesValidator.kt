package com.talkdesk.onboarding.call.config.validation

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class SortValuesValidator : ConstraintValidator<SortValues, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {

        return if (value == null)
            true
        else
            SortFields.toEnum(value) != null
    }
}