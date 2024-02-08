package com.ilooksyou.service;

import com.ilooksyou.dto.GoogleSignin;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.webtoken.JsonWebToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

@Service
public class GoogleSignInService {

    private final String googleClientId;
    public GoogleSignInService(@Value("${oauth.google.client-id}") String googleClientId){
        this.googleClientId = googleClientId;
    }

    public Optional<GoogleSignin> validateToken(String token){
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(this.googleClientId)).build();

        try{
            System.out.println(token);
            GoogleIdToken googleIdToken = verifier.verify(token);
            JsonWebToken.Payload payload = googleIdToken.getPayload();
            GoogleSignin googleSignin = new GoogleSignin(String.valueOf(payload.get("name")),String.valueOf(payload.get("email")),String.valueOf(payload.get("picture")));
            return Optional.ofNullable(googleSignin);
        }catch (GeneralSecurityException | IOException | IllegalArgumentException generalSecurityException){
            generalSecurityException.printStackTrace();
        }
        return Optional.empty();
    }
}
