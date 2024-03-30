package im.pickside.server.topic.validation;

import im.pickside.server.annotation.NoBadWords;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoBadWordsValidator implements ConstraintValidator<NoBadWords, String> {

    public static final String CONTENT_MUST_NOT_BE_EMPTY = "항목을 입력해주세요.";
    public static final String BAD_WORDS_NOT_ALLOWED = "비속어는 사용할 수 없습니다.";

    private final BadWordsHolder badWordsHolder;

    @Override
    public boolean isValid(String content, ConstraintValidatorContext context) {
        if (checkIfNullOrEmpty(content)) {
            setCustomConstraintViolationMessage(context, CONTENT_MUST_NOT_BE_EMPTY);
            return false;
        }
        if (badWordsHolder.containsBadWords(content)) {
            setCustomConstraintViolationMessage(context, BAD_WORDS_NOT_ALLOWED);
            return false;
        }
        return true;
    }

    private boolean checkIfNullOrEmpty(String content) {
        return content == null || content.isEmpty();
    }

    /**
     * 기본 제약 조건 위반 메세지를 비활성화하고, 사용자 정의 메세지를 설정한다.
     * @param context ConstraintValidatorContext
     * @param message 사용자 정의 메세지
     */
    private void setCustomConstraintViolationMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }

}
