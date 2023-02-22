package com.piersoft.converse.vendor.service;

import com.piersoft.converse.vendor.request.dto.ConfirmSignUp;
import com.piersoft.converse.vendor.request.dto.SignIn;
import com.piersoft.converse.vendor.request.dto.SignUp;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ConfirmSignUpResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpResponse;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface AuthService {

    SignUpResponse signUp(SignUp signUp) throws NoSuchAlgorithmException, InvalidKeyException;

    ConfirmSignUpResponse confirmSignUp(ConfirmSignUp confirmSignUp) throws NoSuchAlgorithmException, InvalidKeyException;

    InitiateAuthResponse signIn(SignIn signIn) throws NoSuchAlgorithmException, InvalidKeyException;

}
