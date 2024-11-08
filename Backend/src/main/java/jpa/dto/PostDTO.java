package jpa.dto;

import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostDTO {

    private Long id;
    private String title;
    private String description;
    private MultipartFile image; // Used for image upload
    private String imagePath; // Stores the path to the image file after upload
    private String location; // Optional textual location input
    private Double latitude; // Coordinate latitude
    private Double longitude; // Coordinate longitude
    private LocalDateTime createdAt;
    private Integer likes;
    private List<CommentDTO> comments = new ArrayList<>();
    private String authorUsername; // Stores the username of the author

    public PostDTO() {}

    public PostDTO(Long id, String title, String description, MultipartFile image, String imagePath, String location,
                   Double latitude, Double longitude, LocalDateTime createdAt, Integer likes, List<CommentDTO> comments,
                   String authorUsername) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.imagePath = imagePath;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.likes = likes;
        this.comments = comments;
        this.authorUsername = authorUsername;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
}

