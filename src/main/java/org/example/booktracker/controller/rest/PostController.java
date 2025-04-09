package org.example.booktracker.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.service.PostServiceClient;
import org.example.booktracker.utils.ResponseDto;
import org.example.booktracker.utils.SendPostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {
    final Logger logger = Logger.getLogger(PostController.class.getName());
    private final PostServiceClient postServiceClient;

    @PostMapping
    public ResponseEntity<ResponseDto> sendPost(
            @RequestBody SendPostDto sendPostDto
    ) {
        logger.info(() -> "Post request for sending post: %s".formatted(sendPostDto));
        return ResponseEntity.ok(postServiceClient.sendPost(sendPostDto));
    }
}
