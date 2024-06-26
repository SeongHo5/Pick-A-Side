package im.pickside.server.user.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    ROLE_USER(Authority.ROLE_USER),
    ROLE_MANAGER(Authority.ROLE_MANAGER),
    ROLE_ADMIN(Authority.ROLE_ADMIN);

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    private static class Authority {
        public static final String ROLE_USER = "USER";
        public static final String ROLE_MANAGER = "ROLE_MANAGER";
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
    }
}
