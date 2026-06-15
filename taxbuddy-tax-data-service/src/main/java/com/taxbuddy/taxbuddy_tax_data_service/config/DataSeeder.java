package com.taxbuddy.taxbuddy_tax_data_service.config;



import com.taxbuddy.taxbuddy_tax_data_service.entity.FilingStatus;
import com.taxbuddy.taxbuddy_tax_data_service.entity.TaxProfile;
import com.taxbuddy.taxbuddy_tax_data_service.entity.TaxRegime;
import com.taxbuddy.taxbuddy_tax_data_service.repository.TaxProfileRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component

@RequiredArgsConstructor

@Slf4j

public class DataSeeder implements CommandLineRunner {

    private final TaxProfileRepository taxProfileRepository;

    @Override

    public void run(String... args) {

        if (taxProfileRepository.existsByUserId(1L)) {

            log.info("Tax profile already seeded, skipping...");

            return;

        }

        TaxProfile profile = TaxProfile.builder()

                .userId(1L)

                .fullName("John Doe")

                .panNumber("ABCDE1234F")

                .financialYear("2024-25")

                .grossSalary(new BigDecimal("1200000.00"))

                .basicSalary(new BigDecimal("600000.00"))

                .hra(new BigDecimal("240000.00"))

                .standardDeduction(new BigDecimal("50000.00"))

                .section80C(new BigDecimal("150000.00"))

                .section80D(new BigDecimal("25000.00"))

                .taxableIncome(new BigDecimal("735000.00"))

                .taxPayable(new BigDecimal("75400.00"))

                .tdsPaid(new BigDecimal("80000.00"))

                .refundOrDue(new BigDecimal("4600.00"))

                .taxRegime(TaxRegime.NEW_REGIME)

                .filingStatus(FilingStatus.NOT_FILED)

                .build();

        taxProfileRepository.save(profile);

        log.info("Tax profile seeded successfully for userId: 1");

    }

}

