package com.voter.app.repo;

import com.voter.app.model.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AppUserRepository extends JpaRepository<AppUsers, Long> {

    AppUsers findByEmail(String userId);
}
