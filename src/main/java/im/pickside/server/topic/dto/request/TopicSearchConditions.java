package im.pickside.server.topic.dto.request;

import im.pickside.server.topic.enums.TopicStatus;

public record TopicSearchConditions(String keyword, TopicStatus status, String sort) {

    public static TopicSearchConditions from(String keyword, TopicStatus status, String sort) {
        return new TopicSearchConditions(keyword, status, sort);
    }

}
