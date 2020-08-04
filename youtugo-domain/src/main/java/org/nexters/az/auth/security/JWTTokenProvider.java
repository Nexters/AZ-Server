package org.nexters.az.auth.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.nexters.az.auth.dto.AccessToken;
import org.nexters.az.auth.exception.TokenHasExpiredException;
import org.nexters.az.auth.exception.TokenIsInvalidException;
import org.nexters.az.user.entity.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTTokenProvider {
    private static final String ID = "userId";

    private JWTTokenProvider(){}

    private static class InnerInstance{
        private static final JWTTokenProvider INSTANCE = new JWTTokenProvider();
    }

    public static JWTTokenProvider getInstance(){
        return InnerInstance.INSTANCE;
    }

    public AccessToken generateAccessKey(User user, String secretKey, int expirationDate){
        Map<String, Object> claims = new HashMap<>();
        claims.put(ID, user.getId().toString());

        return generateAccesskey(claims, secretKey, expirationDate);
    }

    private AccessToken generateAccesskey(Map<String, Object> claims, String secretKey, int expirationDate){
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expirationDate);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject("AccessToken")
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(getSigningKey(secretKey))
                .compact();

        return new AccessToken(token, expireDate.getTime());
    }

    public String generateRefreshToken(User user, String secretKey){
        Map<String, Object> claims = new HashMap<>();
        claims.put(ID, user.getId().toString());

        return generateRefreshToken(claims, secretKey);
    }

    private String generateRefreshToken(Map<String, Object> claims, String secretKey){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject("RefreshToken")
                .setIssuedAt(new Date())
                .signWith(getSigningKey(secretKey))
                .compact();
    }

    public long getUserIdByClaims(Claims claims, String subject){
        if(!(claims.getSubject().equals(subject)))
            throw new TokenIsInvalidException("token subject do not match.");

        return Long.parseLong(claims.get(ID).toString());
    }

    public Claims decodingToken(String token, String secretKey){
        try {
            return Jwts.parser()
                    .setSigningKey(getSigningKey(secretKey))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException e) {
            throw new TokenIsInvalidException();
        } catch (ExpiredJwtException e) {
            throw new TokenHasExpiredException();
        } catch (UnsupportedJwtException e) {
            throw new TokenIsInvalidException("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            throw new TokenIsInvalidException("JWT claims string is empty.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new TokenIsInvalidException();
        }
    }

    private SecretKey getSigningKey(String secretKey){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
