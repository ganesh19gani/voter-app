package com.voter.app.repo;

import com.voter.app.model.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUsers, Long> {
    AppUsers findByUserPhoneNumber(String userId);

    AppUsers findByEmail(String userId);
}
