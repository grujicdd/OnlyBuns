package jpa.controller;

import jpa.dto.UserDTO;
import jpa.mapper.UserDTOMapper;
import jpa.model.User;
import jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://127.0.0.1:5173") // Izmenite po potrebi
public class UserController {

    @Autowired
    private UserService userService;

    // Dohvatanje svih korisnika (bez obrisanih)
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.findAllActiveUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(UserDTOMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    // Dohvatanje jednog korisnika po ID-u
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.findOne(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(UserDTOMapper.toDTO(user), HttpStatus.OK);
    }

    // Kreiranje novog korisnika
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = UserDTOMapper.fromDTO(userDTO);
        user = userService.save(user);
        return new ResponseEntity<>(UserDTOMapper.toDTO(user), HttpStatus.CREATED);
    }

    // Ažuriranje postojećeg korisnika
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User user = userService.findOne(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user = userService.save(user);
        return new ResponseEntity<>(UserDTOMapper.toDTO(user), HttpStatus.OK);
    }

    // Brisanje korisnika (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User user = userService.findOne(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/postCounts")
    public Map<Long, Integer> getPostCounts() {
        return userService.getPostCounts();
    }
}
