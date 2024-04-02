package im.pickside.server.user.entity;

import im.pickside.server.common.entity.BaseEntity;
import im.pickside.server.user.enums.RegisterType;
import im.pickside.server.user.enums.UserRole;
import im.pickside.server.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.sql.Timestamp;
import java.time.LocalDate;


@Getter
@Entity
@Builder
@Comment("사용자")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @Comment("사용자 ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("사용자 OAuth ID")
    @Column(nullable = false, length = 50)
    private String accountOauthId;

    @Comment("사용자 가입 구분")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RegisterType registType;

    @Comment("사용자 이름")
    @Column(nullable = false, length = 20)
    private String name;

    @Comment("사용자 이메일")
    @Column(nullable = false, length = 50)
    private String email;

    @Comment("사용자 연락처")
    @Column(nullable = false, length = 30)
    private String contact;

    @Comment("계정 상태")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserStatus userStatus;

    @Comment("계정 제한 상태")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole userRole;

    @Comment("계정 이용 제한 종료일")
    private LocalDate restrictedUntil;

    @Comment("계정 탈퇴일")
    private Timestamp deletedAt;

}
