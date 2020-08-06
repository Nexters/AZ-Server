package org.nexters.az.post.service;

import lombok.RequiredArgsConstructor;
import org.nexters.az.post.entity.Post;
import org.nexters.az.post.entity.PostBookMark;
import org.nexters.az.post.exception.NonExistentBookMarkException;
import org.nexters.az.post.exception.NonExistentPostException;
import org.nexters.az.post.exception.UserAlreadyPressBookMarkException;
import org.nexters.az.post.repository.PostBookMarkRepository;
import org.nexters.az.post.repository.PostRepository;
import org.nexters.az.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class PostBookMarkService {

    private final PostRepository postRepository;
    private final PostBookMarkRepository postBookMarkRepository;

    public Post insertBookMarkInPost(User user, Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(NonExistentPostException::new);

        if (checkUserPressBookMark(user.getId(), postId)) {
            throw new UserAlreadyPressBookMarkException();
        }

        PostBookMark postBookMark = PostBookMark.builder()
                .user(user)
                .post(post)
                .build();

        postBookMarkRepository.save(postBookMark);

        return post;
    }

    public Boolean checkUserPressBookMark(Long userId, Long postId){

        return postBookMarkRepository.existsByUserIdAndPostId(userId,postId);
    }

    public Page<PostBookMark> getBookMarks(Long userId,Pageable pageable){
        return postBookMarkRepository.findAllByUserId(userId,pageable);
    }

    public int countPostBookMark(Long postId) {
        return postBookMarkRepository.countPostBookMarksByPostId(postId);
    }

    public boolean deleteBookMark(User user, Long postId) {
        PostBookMark postBookMark = postBookMarkRepository.findByUserIdAndPostId(user.getId(),postId).orElseThrow(NonExistentBookMarkException::new);
        postBookMarkRepository.delete(postBookMark);
        return true;
    }
}
