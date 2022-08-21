package com.songwriter.backend.validators;


import com.songwriter.backend.annotations.ValidPasswordMatches;
import com.songwriter.backend.payload.request.SignupRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements  ConstraintValidator<ValidPasswordMatches, Object> {

    @Override
    public void initialize(ValidPasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        SignupRequest userSignupRequest = (SignupRequest) obj;
        return userSignupRequest.getPassword().equals(userSignupRequest.getConfirmPassword());
    }
}
