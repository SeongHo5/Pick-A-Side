package im.pickside.server.topic.service;

import im.pickside.server.topic.dto.request.RequestNewTopic;
import im.pickside.server.topic.dto.response.TopicResponse;
import im.pickside.server.topic.dto.request.TopicSearchConditions;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;

@Transactional
public interface TopicService {

    void createTopic(RequestNewTopic requestNewTopic);

    Page<TopicResponse> getTopicsByConditions(TopicSearchConditions conditions, Pageable pageable);

}
