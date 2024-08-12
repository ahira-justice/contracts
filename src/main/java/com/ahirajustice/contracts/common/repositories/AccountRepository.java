package com.ahirajustice.contracts.common.repositories;

import com.ahirajustice.contracts.common.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, QuerydslPredicateExecutor<Account> {

    boolean existsByAccountNumber(String accountNumber);
    Optional<Account> findByAccountNumber(String accountNumber);
    @Query("SELECT acc FROM Account acc WHERE acc.accountNumber IN ?1")
    List<Account> findByAccountNumberIn(Set<String> accountNumbers);

}
