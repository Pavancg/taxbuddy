package com.taxbuddy.taxbuddy_tax_data_service.service;

import com.taxbuddy.taxbuddy_tax_data_service.dto.TaxProfileResponse;
import com.taxbuddy.taxbuddy_tax_data_service.dto.TaxSummaryResponse;
import com.taxbuddy.taxbuddy_tax_data_service.repository.TaxProfileRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Service

@RequiredArgsConstructor

@Slf4j

public class TaxDataService {

    private final TaxProfileRepository taxProfileRepository;

    public TaxProfileResponse getTaxProfile(Long userId) {

        log.info("Fetching tax profile for userId: {}", userId);

        return taxProfileRepository.findByUserId(userId)

                .map(TaxProfileResponse::fromEntity)

                .orElseThrow(() -> new RuntimeException(

                        "Tax profile not found for userId: " + userId));

    }

    public TaxProfileResponse getTaxProfileByYear(

            Long userId, String financialYear) {

        log.info("Fetching tax profile for userId: {} year: {}",

                userId, financialYear);

        return taxProfileRepository

                .findByUserIdAndFinancialYear(userId, financialYear)

                .map(TaxProfileResponse::fromEntity)

                .orElseThrow(() -> new RuntimeException(

                        "Tax profile not found for userId: "

                                + userId + " year: " + financialYear));

    }

    public TaxSummaryResponse getTaxSummary(Long userId) {

        log.info("Fetching tax summary for userId: {}", userId);

        return taxProfileRepository.findByUserId(userId)

                .map(TaxSummaryResponse::fromEntity)

                .orElseThrow(() -> new RuntimeException(

                        "Tax summary not found for userId: " + userId));

    }

    public List<TaxProfileResponse> getAllTaxProfiles(Long userId) {

        log.info("Fetching all tax profiles for userId: {}", userId);

        return taxProfileRepository.findAllByUserId(userId)

                .stream()

                .map(TaxProfileResponse::fromEntity)

                .toList();

    }

}

