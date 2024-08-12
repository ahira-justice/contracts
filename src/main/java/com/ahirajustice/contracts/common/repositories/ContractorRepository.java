package com.ahirajustice.contracts.common.repositories;

import com.ahirajustice.contracts.common.entities.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, Long>, QuerydslPredicateExecutor<Contractor> {

}
