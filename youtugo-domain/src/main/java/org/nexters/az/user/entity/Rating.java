package org.nexters.az.user.entity;

import lombok.Getter;

@Getter
public enum Rating {
    NEW_RECRUIT("신입사원", "어이신입ㅋ\n유머 좁 하나?", 0, 0),
    ASSISTANT_MANAGE("대리", "어이대리~\n반려다 니 유머", 5, 3),
    DEPARTMENT_HEAD("부장", "부장님ㅋㅎ\nMZ세대는 달라^^", 10 ,15),
    MANAGING_DIRECTOR("상무", "이야~상무님!\n나이스샷ㅎ", 50, 50),
    BOSS("사장", "사장님.\n탁월하십니다.", 100, 100);

    private String name;
    private String message;
    private int postCount;
    private int commentCount;

    Rating(String name, String message, int postCount, int commentCount) {
        this.name = name;
        this.message = message;
        this.postCount = postCount;
        this.commentCount = commentCount;
    }
}
