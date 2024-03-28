package im.pickside.server.exception;

import lombok.Getter;


@Getter
public class AuthException extends ApplicationException {

    public AuthException(ExceptionStatus status) {
        super(status);
    }
}
