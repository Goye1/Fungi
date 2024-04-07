package com.Fungi.Fungi.persistance.repository;
import com.Fungi.Fungi.persistance.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {

    @Transactional
    @Modifying
    @Query("delete from User u where u.email = :email")
    void deleteByEmail(@Param("email") String email);
    Optional<User> findByEmail(String username);
    Boolean existsByEmail(String email);


}
