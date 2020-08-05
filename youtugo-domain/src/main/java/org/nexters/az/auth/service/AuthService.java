package org.nexters.az.auth.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.nexters.az.auth.dto.AccessToken;
import org.nexters.az.auth.security.JWTTokenProvider;
import org.nexters.az.auth.security.TokenSubject;
import org.nexters.az.user.entity.User;
import org.nexters.az.user.exception.NonExistentUserException;
import org.nexters.az.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    @Value("${secrets.JWT_KEY}")
    private String secretKey;

    @Value("${secrets.JWT_EXPIRATION}")
    private int expirationDate;

    private final UserRepository userRepository;

    public AccessToken generateAccessTokenBy(String refreshToken) {
        User user = findUserByToken(refreshToken, TokenSubject.REFRESH_TOKEN);

        return generateAccessTokenBy(user);
    }

    public String generateRefreshToken(User user) {
        return JWTTokenProvider.getInstance().generateRefreshToken(user, secretKey);
    }

    public AccessToken generateAccessTokenBy(User user) {
        return JWTTokenProvider.getInstance().generateAccessKey(user, secretKey, expirationDate);
    }

    public User findUserByToken(String token, TokenSubject tokenSubject) {
        Long userId = findUserIdBy(token, tokenSubject);

        return userRepository.findById(userId).orElseThrow(NonExistentUserException::new);
    }

    public Long findUserIdBy(String token, TokenSubject tokenSubject) {
        Claims claims = JWTTokenProvider.getInstance().decodingToken(token, secretKey);

        return JWTTokenProvider.getInstance().getUserIdByClaims(claims, tokenSubject.getSubject());
    }
}
