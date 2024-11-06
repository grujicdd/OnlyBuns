package jpa.repository;

import jpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Pronalaženje svih aktivnih korisnika (deleted = false)
    @Query("SELECT u FROM User u WHERE u.deleted = false")
    List<User> findAllActive();

    // Pronalaženje svih obrisanih korisnika (deleted = true)
    @Query("SELECT u FROM User u WHERE u.deleted = true")
    List<User> findAllDeleted();

    // Vraćanje korisnika po korisničkom imenu (aktivnih)
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.deleted = false")
    User findByUsername(String username);

    // Obnavljanje korisnika (soft delete reverse)
    @Modifying
    @Query("UPDATE User u SET u.deleted = false WHERE u.id = :id")
    void restoreById(Long id);

    // Pronalaženje korisnika po roli (aktivnih)
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.deleted = false")
    List<User> findByRole(String role);
}

