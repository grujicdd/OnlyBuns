package jpa.service;

import jpa.dto.PostDTO;
import jpa.mapper.PostMapper;
import jpa.model.Post;
import jpa.model.User;
import jpa.repository.PostRepository;
import jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private static final String UPLOAD_DIR = "uploads/"; // Directory to save uploaded images

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public PostDTO createPost(PostDTO postDTO) {
        Optional<User> author = userRepository.findByUsername(postDTO.getAuthorUsername());

        if (author.isPresent()) {
            Post post = PostMapper.fromPostDTOToPost(postDTO, author.get());
            post.setCreatedAt(LocalDateTime.now());
            post.setLikes(0);

            MultipartFile imageFile = postDTO.getImage();
            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = saveImageFile(imageFile);
                post.setImagePath(imagePath);
            }

            Post savedPost = postRepository.save(post);
            return PostMapper.fromPostToPostDTO(savedPost);
        } else {
            throw new RuntimeException("Author not found");
        }
    }

    // Retrieve all posts
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostMapper::fromPostToPostDTO)
                .collect(Collectors.toList());
    }

    // Retrieve a post by ID
    public PostDTO getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(PostMapper::fromPostToPostDTO).orElse(null);
    }

    // Update an existing post
    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setTitle(postDTO.getTitle());
            post.setDescription(postDTO.getDescription());
            post.setLongitude(postDTO.getLongitude());
            post.setLatitude(postDTO.getLatitude());

            // Handle image file update if a new file is provided
            MultipartFile imageFile = postDTO.getImage();
            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = saveImageFile(imageFile);
                post.setImagePath(imagePath);
            }

            Post updatedPost = postRepository.save(post);
            return PostMapper.fromPostToPostDTO(updatedPost);
        }
        return null;
    }

    // Soft delete a post
    public boolean deletePost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            Post existingPost = post.get();
            existingPost.setDeleted(true);
            postRepository.save(existingPost);
            return true;
        }
        return false;
    }

    // Increment like count with concurrency handling
    @Transactional
    public void likePost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            Post existingPost = post.get();
            existingPost.setLikes(existingPost.getLikes() + 1);
            postRepository.save(existingPost);
        }
    }

    // Save image file to the server and return the path
    private String saveImageFile(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filePath = UPLOAD_DIR + file.getOriginalFilename();
            Path path = Paths.get(filePath);
            file.transferTo(path);

            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image file", e);
        }
    }
}
