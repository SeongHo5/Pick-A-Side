package im.pickside.server.exception;

import lombok.Getter;

@Getter
public class ServiceFailedException extends ApplicationException {

    public ServiceFailedException(ExceptionStatus ex) {
        super(ex);
    }
}
