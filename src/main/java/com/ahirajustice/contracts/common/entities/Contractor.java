package com.ahirajustice.contracts.common.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contractors")
public class Contractor extends BaseEntity {

    @Column(nullable = false)
    private String profession;
    @OneToOne
    @JoinColumn(nullable = false)
    private Account account;
    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

}
