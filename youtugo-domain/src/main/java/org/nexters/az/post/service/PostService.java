package org.nexters.az.post.service;

import lombok.RequiredArgsConstructor;
import org.nexters.az.comment.repository.CommentRepository;
import org.nexters.az.post.dto.DetailedPost;
import org.nexters.az.post.entity.Post;
import org.nexters.az.post.entity.PostLike;
import org.nexters.az.post.exception.NonExistentPostException;
import org.nexters.az.post.exception.UserAlreadyPressLikeException;
import org.nexters.az.post.repository.PostBookMarkRepository;
import org.nexters.az.post.repository.PostLikeRepository;
import org.nexters.az.post.repository.PostRepository;
import org.nexters.az.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostBookMarkRepository postBookMarkRepository;
    private final CommentRepository commentRepository;

    public List<DetailedPost> detailedPostsOf(List<Post> posts, Long userId) {
        List<DetailedPost> detailedPosts = new ArrayList<>();
        posts.forEach(post -> detailedPosts.add(detailedPostOf(post, userId)));
        detailedPosts.forEach(DetailedPost::makeSimpleContent);

        return detailedPosts;
    }

    public DetailedPost detailedPostOf(Post post, Long userId) {
        DetailedPost detailedPost = new DetailedPost(post);
        detailedPost.setLikes(countPostLike(post.getId()));
        detailedPost.setBookMarkCount(postBookMarkRepository.countPostBookMarksByPostId(post.getId()));
        detailedPost.setCommentCount(commentRepository.countAllByPostId(post.getId()));
        if (userId != null) {
            detailedPost.setPressLike(checkUserPressLike(userId, post.getId()));
            detailedPost.setPressBookMark(checkUserPressBookMark(userId, post.getId()));
        }

        return detailedPost;
    }

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

    public boolean checkUserPressBookMark(Long userId, Long postId) {
        return postBookMarkRepository.existsByUserIdAndPostId(userId, postId);
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

    public Page<Post> getPostsByAuthor(Long authorId, Pageable pageable) {
        return postRepository.findAllByAuthorId(authorId,pageable);
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
