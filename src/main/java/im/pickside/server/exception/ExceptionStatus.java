package im.pickside.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionStatus {

    // 400 - Bad Request : 잘못된 요청
    INVALID_INPUT_VALUE(400, "입력 값이 잘못되었습니다."),
    INVALID_OAUTH_TYPE(400, "소셜 로그인 제공자가 일치하지 않습니다."),
    NO_FILE_TO_UPLOAD(400, "업로드할 파일이 없습니다."),

    // 401 - Unauthorized : 비인증(인증 수단이 없음)
    INVALID_AUTH_ERROR(401, "지원 되지 않거나 잘못된 인증 수단입니다."),
    INVALID_EMAIL_VERIFICATION_CODE(401, "이메일 인증 코드가 일치하지 않습니다."),

    // 403 - Forbidden : 권한 없음 (서버가 요청을 이해했지만 승인을 거부)
    RESTRICTED_ACCOUNT(403, "이용 제한된 계정입니다."),
    PROHIBITED_USERNAME(403, "사용할 수 없는 이름입니다."),
    EMAIL_ALREADY_VERIFIED(403, "이미 인증된 이메일입니다."),
    EMAIL_ALREADY_SENT(403, "이미 이메일이 전송되었습니다. 3분 후에 다시 시도해주세요."),
    BLACKLISTED_TOKEN(403, "무효화된 토큰"),
    EXPIRED_TOKEN(403, "만료된 토큰"),
    EXPIRED_REFRESH_TOKEN(403, "리프레시 토큰 만료"),
    CHANGE_ROLE_FORBIDDEN(403, "소셜 가입 계정은 권한을 변경할 수 없습니다."),

    // 404 - Not Found : 잘못된 리소스 접근
    NOT_FOUND_ACCOUNT(404, "존재하지 않는 계정입니다."),
    NOT_FOUND_REDIS_KEY(404, "존재하지 않는 REDIS KEY 입니다."),

    // 405 - Method Not Allowed
    METHOD_NOT_ALLOWED(405, "허용되지 않는 HTTP 메서드입니다."),

    // 409 - Conflict : 중복 데이터
    CONFLICT_ACCOUNT(409, "이미 가입한 계정입니다."),
    LOCAL_ACCOUNT_ALREADY_EXIST(409, "이미 등록된 계정입니다."),
    DELETED_ACCOUNT(409, "이미 탈퇴한 계정입니다."),

    // 413 - Payload Too Large
    TOO_MANY_FILES(413, "파일은 최대 3개만 업로드 할 수 있습니다."),
    FILE_TOO_LARGE(413, "파일은 최대 3MB까지 업로드 할 수 있습니다."),

    // 415 - Unsupported Media Type
    UNSUPPORTED_FILE_FORMAT(415, "지원하지 않는 파일 형식입니다."),

    // 429 - Too Many Requests
    TOO_MANY_REQUESTS(429, "요청 횟수를 초과하였습니다. 잠시 후 다시 시도해주세요."),

    // 500 - Internal Server Error
    FAIL_TO_SEND_EMAIL(500, "이메일 전송 실패"),
    FAIL_TO_CONSTRUCT_EMAIL(500, "이메일 생성 실패"),
    FAILED_TO_UPLOAD_FILE(500, "파일 업로드 실패"),
    FAILED_TO_EXECUTE_REQUESTED_ACTION(500, "요청한 작업을 수행할 수 없습니다."),
    FAILED_HTTP_ACTION(500, "HTTP 요청 실패");

    private final Integer statusCode;
    private final String message;
}
