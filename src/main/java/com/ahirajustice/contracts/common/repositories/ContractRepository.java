package com.ahirajustice.contracts.common.repositories;

import com.ahirajustice.contracts.common.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long>, QuerydslPredicateExecutor<Contract> {

}
