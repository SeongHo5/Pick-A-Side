package im.pickside.server.topic.service;

import im.pickside.server.topic.dto.request.RequestNewTopic;
import im.pickside.server.topic.dto.request.TopicSearchConditions;
import im.pickside.server.topic.dto.response.TopicResponse;
import im.pickside.server.topic.entity.Topic;
import im.pickside.server.topic.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    @Override
    public void createTopic(RequestNewTopic requestNewTopic) {
        topicRepository.save(Topic.of(requestNewTopic));
    }

    @Override
    public Page<TopicResponse> getTopicsByConditions(TopicSearchConditions conditions, Pageable pageable) {
        return null;
    }
}
