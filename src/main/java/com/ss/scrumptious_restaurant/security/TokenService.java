package com.ss.scrumptious_restaurant.security;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class TokenService {


    @Autowired
    private SecurityConstants securityConstants;

    @Autowired
    private HttpServletRequest servletRequest;

    public TokenModel parseToken(){
        String header = servletRequest.getHeader(securityConstants.getHEADER_STRING());

        String token = header.replace(securityConstants.getTOKEN_PREFIX(), "");
        Algorithm algorithm = Algorithm.HMAC512(securityConstants.getSECRET());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        String email = jwt.getSubject();
        Date exp = jwt.getExpiresAt();
        String roleName = jwt.getClaims().get(securityConstants.getAUTHORITY_CLAIM_KEY()).asString();
        String uid = jwt.getClaims().get(securityConstants.getUSER_ID_CLAIM_KEY()).asString();
        TokenModel model = TokenModel.builder().email(email)
                .expiration_date(exp)
                .role(roleName.replace("ROLE_", ""))
                .uid(UUID.fromString(uid))
                .build();
        return model;

    }

}