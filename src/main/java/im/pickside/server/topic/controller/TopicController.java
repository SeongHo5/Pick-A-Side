package im.pickside.server.topic.controller;

import im.pickside.server.topic.service.TopicService;
import im.pickside.server.topic.enums.TopicStatus;
import im.pickside.server.topic.dto.request.RequestNewTopic;
import im.pickside.server.topic.dto.response.TopicResponse;
import im.pickside.server.topic.dto.request.TopicSearchConditions;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    @PostMapping
    public ResponseEntity<Void> createTopic(@RequestBody @Valid final RequestNewTopic requestNewTopic) {
        topicService.createTopic(requestNewTopic);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<TopicResponse>> getTopicsByConditions(
            Pageable pageable,
            @RequestParam(required = false) final String keyword,
            @RequestParam(required = false) final TopicStatus status,
            @RequestParam(required = false) final String sort
    ) {
        return ResponseEntity.ok()
                .body(topicService.getTopicsByConditions(TopicSearchConditions.from(keyword, status, sort), pageable));
    }


}
