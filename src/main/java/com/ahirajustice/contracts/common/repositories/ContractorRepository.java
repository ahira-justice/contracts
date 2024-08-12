package com.ahirajustice.contracts.common.repositories;

import com.ahirajustice.contracts.common.entities.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, Long>, QuerydslPredicateExecutor<Contractor> {

    @Query("SELECT c FROM Contractor c WHERE c.user.username = ?1")
    Optional<Contractor> findByUsername(String username);

}
