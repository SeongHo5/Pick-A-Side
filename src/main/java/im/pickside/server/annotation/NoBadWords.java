package im.pickside.server.annotation;

import im.pickside.server.topic.validation.NoBadWordsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoBadWordsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface NoBadWords {

    String message() default "비속어는 사용할 수 없습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
