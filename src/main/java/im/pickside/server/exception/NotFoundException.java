package im.pickside.server.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends ApplicationException {

    public NotFoundException(ExceptionStatus status) {
        super(status);
    }
}
