package com.piersoft.converse.vendor.service.impl;

import com.piersoft.converse.vendor.request.dto.ConfirmSignUp;
import com.piersoft.converse.vendor.request.dto.SignIn;
import com.piersoft.converse.vendor.request.dto.SignUp;
import com.piersoft.converse.vendor.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private String clientId = "2p1hrkl29ugo79h6hg52mog031";
    private String clientSecret = "qfqf3g0h5d7vb2eeoqlde8hld14se12a5j9afa8qu9ri52mmt1d";

    private String userPoolId = "us-east-1_YfNCs53wu";
    @Autowired
    private CognitoIdentityProviderClient amazonCognitoIdentityClient;


    @Override
    public SignUpResponse signUp(SignUp signUp) throws NoSuchAlgorithmException, InvalidKeyException {

        String username = signUp.getCountryCode()+signUp.getWhatsappNumber();
        SignUpRequest signUpRequest = SignUpRequest.builder().
                clientId(clientId)
                .username(username)
                .password(signUp.getPassword())
                .secretHash(calculateSecretHash(clientId, clientSecret, username))
                .build();
        return  amazonCognitoIdentityClient.signUp(signUpRequest);
    }

    @Override
    public ConfirmSignUpResponse confirmSignUp(ConfirmSignUp confirmSignUp) throws NoSuchAlgorithmException, InvalidKeyException {
        String username = confirmSignUp.getCountryCode()+confirmSignUp.getPhoneNumber();
        ConfirmSignUpRequest req = ConfirmSignUpRequest.builder()
                .clientId(clientId)
                .confirmationCode(confirmSignUp.getConfirmationCode())
                .username(username)
                .secretHash(calculateSecretHash(clientId, clientSecret, username))
                .build();

        return amazonCognitoIdentityClient.confirmSignUp(req);
    }

    @Override
    public InitiateAuthResponse signIn(SignIn signIn) throws NoSuchAlgorithmException, InvalidKeyException {
        String username = signIn.getCountryCode()+signIn.getPhoneNumber();
        Map<String,String> authMap = new HashMap<>();
        authMap.put("USERNAME", username);
        authMap.put("PASSWORD", signIn.getPassword());
        authMap.put("SECRET_HASH",calculateSecretHash(clientId, clientSecret, username));

        InitiateAuthRequest initiateAuthRequest = InitiateAuthRequest.builder()
                .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .authParameters(authMap)
                .clientId(clientId)
                .build();
        return  amazonCognitoIdentityClient.initiateAuth(initiateAuthRequest);
    }

    public static String calculateSecretHash(String userPoolClientId, String userPoolClientSecret, String userName) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeyException {
        final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

        SecretKeySpec signingKey = new SecretKeySpec(
                userPoolClientSecret.getBytes(StandardCharsets.UTF_8),
                HMAC_SHA256_ALGORITHM);

        Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
        mac.init(signingKey);
        mac.update(userName.getBytes(StandardCharsets.UTF_8));
        byte[] rawHmac = mac.doFinal(userPoolClientId.getBytes(StandardCharsets.UTF_8));
        return java.util.Base64.getEncoder().encodeToString(rawHmac);
    }
}
