package jpa.mapper;

import org.springframework.stereotype.Component;
import jpa.dto.UserDTO;
import jpa.model.User;

@Component
public class UserDTOMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(user);
    }

    public static User fromDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
