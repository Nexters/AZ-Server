package org.nexters.az.user.entity;

import lombok.Getter;

@Getter
public enum Rating {
    NEW_RECRUIT("신입사원"),
    ASSISTANT_MANAGE("대리"),
    DEPARTMENT_HEAD("부장"),
    MANAGING_DIRECTOR("상무"),
    BOSS("사장");

    private String name;

    Rating(String name) {
        this.name = name;
    }
}
