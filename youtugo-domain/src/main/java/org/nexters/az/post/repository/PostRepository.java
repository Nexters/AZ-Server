package org.nexters.az.post.repository;

import org.nexters.az.common.repository.ExtendRepository;
import org.nexters.az.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface PostRepository extends ExtendRepository<Post> {
    void deleteByIdAndAuthorId(Long postId, Long userId);

    Page<Post> findAllByAuthorId (Long authorId, Pageable pageable);

    boolean existsById(Long postId);

    int countAllByAuthorId(Long authorId);

    @Transactional
    @Modifying
    @Query("UPDATE Post SET likeCount = likeCount + 1 WHERE id = :id")
    void updateLikeCount(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Post SET viewCount = viewCount + 1 WHERE id = :id")
    void updateViewCount(@Param("id") Long id);

//    @Query(nativeQuery = true, value = "SELECT *, p.likeCount*p.viewCount AS popular from Post p order by popular asc limit:pageNo, 10; ")
//    Page<Post> selectPostByLikeCountAndViewCount(@Param("pageNo")int pageNo);
}
