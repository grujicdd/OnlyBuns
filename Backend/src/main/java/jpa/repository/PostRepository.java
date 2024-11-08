package jpa.repository;

import jpa.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Fetch all posts that are not soft-deleted
    @Query("SELECT p FROM Post p WHERE p.deleted = false")
    List<Post> findAllActive();

    // Fetch posts by author's username, filtering out soft-deleted posts
    @Query("SELECT p FROM Post p WHERE p.author.username = :username AND p.deleted = false")
    List<Post> findByAuthorUsername(String username);

    // Fetch posts that have not been compressed and are older than a specified date
    @Query("SELECT p FROM Post p WHERE p.imagePath IS NOT NULL AND p.isCompressed = false AND p.createdAt < :cutoffDate AND p.deleted = false")
    List<Post> findOldUncompressedImages(LocalDateTime cutoffDate);

    // Soft delete a post by ID
    @Modifying
    @Query("UPDATE Post p SET p.deleted = true WHERE p.id = :id")
    void softDeleteById(Long id);

    // Increment likes for a post by ID, ensuring concurrent updates are safely handled
    @Modifying
    @Query("UPDATE Post p SET p.likes = p.likes + 1 WHERE p.id = :id")
    void incrementLikes(Long id);

    // Optional: Fetch a post by ID, ensuring it is not soft-deleted
    @Query("SELECT p FROM Post p WHERE p.id = :id AND p.deleted = false")
    Post findActivePostById(Long id);

    // Update the image path and set isCompressed flag for a specific post
    @Modifying
    @Query("UPDATE Post p SET p.imagePath = :imagePath, p.isCompressed = true WHERE p.id = :id")
    void updateImagePathAndCompressedFlag(Long id, String imagePath);

    @Query("SELECT p.author.id, COUNT(p) FROM Post p GROUP BY p.author.id")
    List<Object[]> countPostsByUser();
}