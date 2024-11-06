package jpa.dto;

import jpa.model.User;

public class UserDTO {
    private Long id;
    private String username;
    private String email;

    // Default konstruktor
    public UserDTO() {}

    // Konstruktor za konverziju iz User entiteta
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    // Getteri i setteri
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
