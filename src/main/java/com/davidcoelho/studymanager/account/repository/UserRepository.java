package com.davidcoelho.studymanager.account.repository;

import com.davidcoelho.studymanager.account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);
}
