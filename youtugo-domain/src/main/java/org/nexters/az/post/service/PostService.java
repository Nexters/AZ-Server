package org.nexters.az.post.service;

import lombok.RequiredArgsConstructor;
import org.nexters.az.post.entity.Post;
import org.nexters.az.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;

    public Post create(Post post) {
        return postRepository.save(post);
    }
}
