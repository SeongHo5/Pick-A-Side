package im.pickside.server.topic.entity;

import im.pickside.server.annotation.NoBadWords;
import im.pickside.server.common.entity.SecureBaseEntity;
import im.pickside.server.topic.enums.TopicStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "topic",
        indexes = {
                @Index(name = "idx_title", columnList = "title")
        }
)
public class Topic extends SecureBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NoBadWords
    @Column(nullable = false, length = 100)
    private String title;

    @NoBadWords
    @Column(nullable = false, length = 50)
    private String optionA;

    @NoBadWords
    @Column(nullable = false, length = 50)
    private String optionB;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TopicStatus status;

    @Column(columnDefinition = "default NULL")
    private LocalDateTime deletedAt;

}
