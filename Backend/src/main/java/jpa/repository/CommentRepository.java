package jpa.repository;

import jpa.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Fetch all comments by post ID that are not deleted (soft delete)
    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.deleted = false")
    List<Comment> findByPostId(Long postId);

    // Fetch comments by author's username that are not deleted
    @Query("SELECT c FROM Comment c WHERE c.authorUsername = :username AND c.deleted = false")
    List<Comment> findByAuthorUsername(String username);

    // Soft delete a comment by ID
    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.deleted = true WHERE c.id = :id")
    void softDeleteById(Long id);
}
