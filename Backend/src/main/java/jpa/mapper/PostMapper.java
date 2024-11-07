package jpa.mapper;

import jpa.dto.PostDTO;
import jpa.model.Post;

public class PostMapper {

    public static Post fromDTO(PostDTO postDTO) {
        return new Post(postDTO.getTitle(), postDTO.getContent(), postDTO.getAuthorUsername());
    }

    public static PostDTO toDTO(Post post) {
        return new PostDTO(post);
    }
}
