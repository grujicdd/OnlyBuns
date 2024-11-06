package jpa.service;

import jpa.model.User;
import jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Pronalazak jednog korisnika po ID-u
    public User findOne(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Dohvatanje svih aktivnih korisnika (koji nisu obrisani)
    public List<User> findAllActiveUsers() {
        return userRepository.findAllActive();
    }

    // Kreiranje ili ažuriranje korisnika
    public User save(User user) {
        return userRepository.save(user);
    }

    // Soft delete korisnika
    public void delete(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setDeleted(true);
            userRepository.save(user);
        }
    }

    public List<User> findAllAdmins() {
        return userRepository.findByRole("ADMIN");
    }

}
