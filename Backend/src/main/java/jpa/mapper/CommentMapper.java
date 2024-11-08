package jpa.mapper;

import jpa.dto.CommentDTO;
import jpa.model.Comment;

public class CommentMapper {

    // Convert Comment entity to CommentDTO
    public static CommentDTO fromCommentToCommentDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getPost().getId(), // ID of the associated post
                comment.getAuthorUsername(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }

    // Convert CommentDTO to Comment entity
    public static Comment fromCommentDTOToComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setAuthorUsername(commentDTO.getAuthor());
        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(commentDTO.getCreatedAt());

        // Note: Post mapping might need additional steps if not directly set here
        return comment;
    }
}
