package com.voter.app.repo;

import com.voter.app.model.Parties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartiesRepository extends JpaRepository<Parties,Long> {
}
