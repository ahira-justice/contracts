package com.ahirajustice.contracts.common.repositories;

import com.ahirajustice.contracts.common.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, QuerydslPredicateExecutor<Client> {

    @Query("SELECT c FROM Client c WHERE c.user.username = ?1")
    Optional<Client> findByUsername(String username);
    
}
