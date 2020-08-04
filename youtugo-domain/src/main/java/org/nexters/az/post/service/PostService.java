package org.nexters.az.post.service;

import lombok.RequiredArgsConstructor;
import org.nexters.az.post.entity.Post;
import org.nexters.az.post.entity.PostLike;
import org.nexters.az.post.exception.NonExistentPostException;
import org.nexters.az.post.exception.UserAlreadyPressLikeException;
import org.nexters.az.post.repository.PostLikeRepository;
import org.nexters.az.post.repository.PostRepository;
import org.nexters.az.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    public Post insertLikeInPost(User user, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(NonExistentPostException::new);

        if (checkUserPressLike(user.getId(), postId)) {
            throw new UserAlreadyPressLikeException();
        }

        PostLike postLike = PostLike.builder()
                .post(post)
                .user(user)
                .build();

        postLikeRepository.save(postLike);

        return post;
    }

    public int countPostLike(Long postId) {
        return postLikeRepository.countPostLikesByPostId(postId);
    }

    public boolean checkUserPressLike(Long userId, Long postId) {
        return postLikeRepository.existsByUserIdAndPostId(userId, postId);
    }

    public Post create(Post post) {
        return postRepository.save(post);
    }

    public Page<Post> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(NonExistentPostException::new);
    }

    public boolean checkExistPost(Long postId) {
        return postRepository.existsById(postId);
    }

    public void deletePost(Long postId, Long userId) {
        postRepository.deleteByIdAndAuthorId(postId, userId);
    }

    public int getPostCountBy(Long userId) {
        return postRepository.countAllByAuthorId(userId);
    }


}
