package jpa.service;

import jpa.model.User;
import jpa.repository.PostRepository;
import jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

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

    public Map<Long, Integer> getPostCounts() {
        List<Object[]> counts = postRepository.countPostsByUser();
        Map<Long, Integer> postCounts = new HashMap<>();

        for (Object[] count : counts) {
            Long userId = (Long) count[0];
            Integer postCount = ((Number) count[1]).intValue();
            postCounts.put(userId, postCount);
        }
        return postCounts;
    }

}
