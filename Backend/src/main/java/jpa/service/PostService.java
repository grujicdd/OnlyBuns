package jpa.service;

import jpa.model.Post;
import jpa.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> findAllActivePosts() {
        return postRepository.findAllActivePosts();
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            post.setDeleted(true);
            postRepository.save(post);
        }
    }

    public List<Post> findPostsByFollowedUsers(List<String> usernames) {
        return postRepository.findPostsByFollowedUsers(usernames);
    }
}
