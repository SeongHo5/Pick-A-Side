package im.pickside.server.topic.dto.request;

import im.pickside.server.annotation.NoBadWords;

public record RequestNewTopic(
        @NoBadWords
        String title,
        @NoBadWords
        String optionA,
        @NoBadWords
        String optionB
) {
}
