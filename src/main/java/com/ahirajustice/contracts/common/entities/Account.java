package com.ahirajustice.contracts.common.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {

    @Column(nullable = false)
    private String accountName;
    @Column(nullable = false, unique = true)
    private String accountNumber;
    @Column(nullable = false)
    private Long balance;
    @Column(nullable = false)
    private LocalDateTime balanceComputedOn;
    @Column(nullable = false)
    private String currencyCode;

}
