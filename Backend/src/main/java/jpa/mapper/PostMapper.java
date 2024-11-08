package jpa.mapper;

import jpa.dto.PostDTO;
import jpa.model.Post;
import jpa.model.User;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    // Converts Post entity to PostDTO
    public static PostDTO fromPostToPostDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                null, // MultipartFile is not needed when converting from Post to PostDTO
                post.getImagePath(), // Set the path to the image file
                null, // Location in textual format, if needed
                post.getLatitude(),
                post.getLongitude(),
                post.getCreatedAt(),
                post.getLikes(),
                post.getComments()
                        .stream()
                        .map(CommentMapper::fromCommentToCommentDTO)
                        .collect(Collectors.toList()),
                post.getAuthor() != null ? post.getAuthor().getUsername() : null // Set author username
        );
    }

    // Converts PostDTO to Post entity
    public static Post fromPostDTOToPost(PostDTO postDTO, User author) {
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());

        // Set the image path if it exists
        if (postDTO.getImagePath() != null) {
            post.setImagePath(postDTO.getImagePath());
        }

        post.setLatitude(postDTO.getLatitude());
        post.setLongitude(postDTO.getLongitude());
        post.setCreatedAt(postDTO.getCreatedAt());
        post.setLikes(postDTO.getLikes());

        post.setComments(
                postDTO.getComments()
                        .stream()
                        .map(CommentMapper::fromCommentDTOToComment)
                        .collect(Collectors.toList())
        );

        // Set the author
        post.setAuthor(author);

        return post;
    }
}

