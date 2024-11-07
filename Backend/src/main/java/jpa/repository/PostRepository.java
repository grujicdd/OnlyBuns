package jpa.repository;

import jpa.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Pronalaženje svih aktivnih (neobrisanih) objava
    @Query("SELECT p FROM Post p WHERE p.deleted = false")
    List<Post> findAllActivePosts();

    // Pronalaženje svih objava korisnika koji su zapraćeni
    @Query("SELECT p FROM Post p WHERE p.authorUsername IN :usernames AND p.deleted = false")
    List<Post> findPostsByFollowedUsers(List<String> usernames);
}
