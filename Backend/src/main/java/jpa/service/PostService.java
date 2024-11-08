package jpa.service;

import jpa.dto.PostDTO;
import jpa.mapper.PostMapper;
import jpa.model.Post;
import jpa.model.User;
import jpa.repository.PostRepository;
import jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private static final String UPLOAD_DIR = "uploads/"; // Directory to save uploaded images
    private static final String COMPRESSED_DIR = "compressed/"; // Directory to save compressed images

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new post
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

    // Compress image files that are older than a month and not yet compressed
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void compressOldImages() {
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            if (post.getImagePath() != null && !post.isCompressed()) {
                LocalDateTime oneMonthAgo = LocalDateTime.now().minus(1, ChronoUnit.MONTHS);
                if (post.getCreatedAt().isBefore(oneMonthAgo)) {
                    try {
                        String compressedPath = compressImage(post.getImagePath());
                        post.setImagePath(compressedPath);
                        post.setCompressed(true);
                        postRepository.save(post);
                        System.out.println("Compressed image for post ID: " + post.getId());
                    } catch (IOException e) {
                        System.err.println("Failed to compress image for post ID: " + post.getId());
                    }
                }
            }
        }
    }

    // Compress image and save it in the compressed directory
    private String compressImage(String originalImagePath) throws IOException {
        Path originalPath = Paths.get(originalImagePath);
        Path compressedPath = Paths.get(COMPRESSED_DIR);

        if (!Files.exists(compressedPath)) {
            Files.createDirectories(compressedPath);
        }

        String compressedFilePath = COMPRESSED_DIR + originalPath.getFileName();
        Path compressedFile = Paths.get(compressedFilePath);

        // Mock compression logic (Replace this with a real library like Thumbnailator or ImageIO)
        Files.copy(originalPath, compressedFile);

        return compressedFilePath;
    }
}

