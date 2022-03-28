package com.globant.final_thesis_work.Security.JWT;

import com.globant.final_thesis_work.Security.Model.UserSessionDetails;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication auth) {
        UserSessionDetails userSessionDetails = (UserSessionDetails) auth.getPrincipal();

        return Jwts.builder().setSubject(userSessionDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            System.err.println("MalformedToken");
        } catch (UnsupportedJwtException e) {
            System.err.println("UnsupportedToken");
        } catch (ExpiredJwtException e) {
            System.err.println("ExpiredToken");
        } catch (IllegalArgumentException e) {
            System.err.println("EmptyToken");
        } catch (SignatureException e) {
            System.err.println("InvalidSignatureToken");
        }
        return false;
    }
}
