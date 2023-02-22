package com.piersoft.converse.vendor.controller;


import com.piersoft.converse.vendor.request.dto.ConfirmSignUp;
import com.piersoft.converse.vendor.request.dto.SignIn;
import com.piersoft.converse.vendor.request.dto.SignUp;
import com.piersoft.converse.vendor.response.dto.SignInResponse;
import com.piersoft.converse.vendor.response.dto.SignUpErrorResponse;
import com.piersoft.converse.vendor.response.dto.SignUpSuccessResponse;
import com.piersoft.converse.vendor.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.awscore.exception.AwsErrorDetails;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/converse/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignUp signUp) throws NoSuchAlgorithmException, InvalidKeyException {
        SignUpResponse signUpResponse = null;
        try {
            signUpResponse = authService.signUp(signUp);
        }catch(UsernameExistsException | InvalidPasswordException ex){
            AwsErrorDetails errorDetails =  ex.awsErrorDetails();
            return ResponseEntity.internalServerError().body(SignUpErrorResponse.builder().errorCode(errorDetails.errorCode()).errorResponse(errorDetails.errorMessage()).build());
        }catch(Exception ex){
            return ResponseEntity.internalServerError().body(SignUpErrorResponse.builder().errorCode(ex.getLocalizedMessage()).errorResponse(ex.getLocalizedMessage()).build());
        }
        return ResponseEntity.ok().body(SignUpSuccessResponse.builder().userId(signUpResponse.userSub()).userConfirmed(signUpResponse.userConfirmed()).build());
    }

    @PostMapping("/confirmSignup")
    public ResponseEntity<Object> confirmSignup(@RequestBody ConfirmSignUp confirmSignUp) throws NoSuchAlgorithmException, InvalidKeyException {
        ConfirmSignUpResponse confirmSignUpResponse = null;
        try {
            confirmSignUpResponse =  authService.confirmSignUp(confirmSignUp);
        }catch(UsernameExistsException | CodeMismatchException ex){
            AwsErrorDetails errorDetails =  ex.awsErrorDetails();
            return ResponseEntity.internalServerError().body(SignUpErrorResponse.builder().errorCode(errorDetails.errorCode()).errorResponse(errorDetails.errorMessage()).build());
        }catch(Exception ex){
            return ResponseEntity.internalServerError().body(SignUpErrorResponse.builder().errorCode(ex.getLocalizedMessage()).errorResponse(ex.getLocalizedMessage()).build());
        }
        return ResponseEntity.ok().body("Successfully confirmed user");
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(@RequestBody SignIn signIn) throws NoSuchAlgorithmException, InvalidKeyException {
        InitiateAuthResponse initiateAuthResponse = null;
        try {
            initiateAuthResponse = authService.signIn(signIn);
        }catch(UserNotConfirmedException | NotAuthorizedException ex){
            AwsErrorDetails errorDetails =  ex.awsErrorDetails();
            return ResponseEntity.internalServerError().body(SignUpErrorResponse.builder().errorCode(errorDetails.errorCode()).errorResponse(errorDetails.errorMessage()).build());
        }catch(Exception ex){
            return ResponseEntity.internalServerError().body(SignUpErrorResponse.builder().errorCode(ex.getLocalizedMessage()).errorResponse(ex.getLocalizedMessage()).build());
        }
        SignInResponse response = SignInResponse.builder()
                .accessToken(initiateAuthResponse.authenticationResult().accessToken())
                .tokenType(initiateAuthResponse.authenticationResult().tokenType())
                .refreshToken(initiateAuthResponse.authenticationResult().refreshToken())
                .idToken(initiateAuthResponse.authenticationResult().idToken())
                .expiresIn(initiateAuthResponse.authenticationResult().expiresIn())
                .build();
        return ResponseEntity.ok().body(response);
    }




}
