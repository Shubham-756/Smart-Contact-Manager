package com.scm.scm20.repsitories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.scm20.entities.User;
import java.util.List;


@Repository
public interface UserRepo extends JpaRepository<User, String>
{
    Optional<User> findByEmail(String email);
}
