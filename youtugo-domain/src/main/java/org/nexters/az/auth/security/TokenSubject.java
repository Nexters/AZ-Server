package org.nexters.az.auth.security;

public enum TokenSubject {
    ACCESS_TOKEN("AccessToken"),
    REFRESH_TOKEN("RefreshToken");

    private String subject;

    TokenSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
}
