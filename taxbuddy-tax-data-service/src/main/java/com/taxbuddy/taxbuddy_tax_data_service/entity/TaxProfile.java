package com.taxbuddy.taxbuddy_tax_data_service.entity;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import java.time.LocalDateTime;

@Entity

@Table(name = "tax_profiles")

@Data

@Builder

@NoArgsConstructor

@AllArgsConstructor

public class TaxProfile {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false, unique = true)

    private Long userId;

    @Column(nullable = false)

    private String fullName;

    @Column(nullable = false)

    private String panNumber;

    @Column(nullable = false)

    private String financialYear;

    @Column(nullable = false, precision = 15, scale = 2)

    private BigDecimal grossSalary;

    @Column(nullable = false, precision = 15, scale = 2)

    private BigDecimal basicSalary;

    @Column(nullable = false, precision = 15, scale = 2)

    private BigDecimal hra;

    @Column(nullable = false, precision = 15, scale = 2)

    private BigDecimal standardDeduction;

    @Column(nullable = false, precision = 15, scale = 2)

    private BigDecimal section80C;

    @Column(nullable = false, precision = 15, scale = 2)

    private BigDecimal section80D;

    @Column(nullable = false, precision = 15, scale = 2)

    private BigDecimal taxableIncome;

    @Column(nullable = false, precision = 15, scale = 2)

    private BigDecimal taxPayable;

    @Column(nullable = false, precision = 15, scale = 2)

    private BigDecimal tdsPaid;

    @Column(nullable = false, precision = 15, scale = 2)

    private BigDecimal refundOrDue;

    @Enumerated(EnumType.STRING)

    @Column(nullable = false)

    private TaxRegime taxRegime;

    @Enumerated(EnumType.STRING)

    @Column(nullable = false)

    private FilingStatus filingStatus;

    @Column(nullable = false, updatable = false)

    private LocalDateTime createdAt;

    @Column(nullable = false)

    private LocalDateTime updatedAt;

    @PrePersist

    protected void onCreate() {

        createdAt = LocalDateTime.now();

        updatedAt = LocalDateTime.now();

    }

    @PreUpdate

    protected void onUpdate() {

        updatedAt = LocalDateTime.now();

    }

}

