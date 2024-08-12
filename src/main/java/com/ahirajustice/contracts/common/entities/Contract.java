package com.ahirajustice.contracts.common.entities;

import com.ahirajustice.contracts.common.enums.ContractStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contracts")
public class Contract extends BaseEntity {

    @Lob
    @Column(nullable = false)
    private String terms;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractStatus status;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Contractor contractor;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Client client;

}
