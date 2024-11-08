package jpa.service;

import jpa.dto.CommentDTO;
import jpa.mapper.CommentMapper;
import jpa.model.Comment;
import jpa.model.Post;
import jpa.repository.CommentRepository;
import jpa.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    // Use static methods directly, so no need for @Autowired here
    private CommentMapper commentMapper;

    // Create a new comment
    @Transactional
    public CommentDTO createComment(CommentDTO commentDTO) {
        Optional<Post> post = postRepository.findById(commentDTO.getPostId());
        if (post.isPresent()) {
            Comment comment = CommentMapper.fromCommentDTOToComment(commentDTO);
            comment.setPost(post.get());
            comment.setCreatedAt(LocalDateTime.now());
            Comment savedComment = commentRepository.save(comment);
            return CommentMapper.fromCommentToCommentDTO(savedComment);
        }
        throw new RuntimeException("Post not found for ID: " + commentDTO.getPostId());
    }

    // Retrieve all comments for a given post
    public List<CommentDTO> getCommentsByPost(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
                .map(CommentMapper::fromCommentToCommentDTO)
                .collect(Collectors.toList());
    }

    // Update an existing comment
    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Optional<Comment> existingComment = commentRepository.findById(id);
        if (existingComment.isPresent()) {
            Comment comment = existingComment.get();
            comment.setContent(commentDTO.getContent());
            Comment updatedComment = commentRepository.save(comment);
            return CommentMapper.fromCommentToCommentDTO(updatedComment);
        }
        return null;
    }

    // Soft delete a comment
    public boolean deleteComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            Comment existingComment = comment.get();
            existingComment.setDeleted(true);
            commentRepository.save(existingComment);
            return true;
        }
        return false;
    }

    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll(); // Fetch all comments from the database
        return comments.stream()
                .map(comment -> new CommentDTO(comment.getId(), comment.getPost().getId(), comment.getAuthorUsername(), comment.getContent(), comment.getCreatedAt()))
                .collect(Collectors.toList());
    }

}
