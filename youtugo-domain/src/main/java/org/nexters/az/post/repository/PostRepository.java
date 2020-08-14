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

    @Query(nativeQuery = true, value = "SELECT *, p.likeCount*p.viewCount AS popular from Post p order by popular asc limit:pageNo, 10; ")
    Page<Post> selectPostByLikeCountAndViewCount(@Param("pageNo") Pageable pageNo);

    @Query("SELECT p FROM Post p ORDER BY p.likeCount*p.viewCount DESC")
    Page<Post> findPopularPosts(Pageable pageable);


}
