package com.taxbuddy.taxbuddy_tax_data_service.controller;


import com.taxbuddy.taxbuddy_tax_data_service.dto.TaxProfileResponse;
import com.taxbuddy.taxbuddy_tax_data_service.dto.TaxSummaryResponse;
import com.taxbuddy.taxbuddy_tax_data_service.service.TaxDataService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tax")
@RequiredArgsConstructor
@Slf4j
public class TaxDataController {

    private final TaxDataService taxDataService;

    @GetMapping("/profile/{userId}")

    public ResponseEntity<TaxProfileResponse> getTaxProfile(

            @PathVariable Long userId) {

        return ResponseEntity.ok(

                taxDataService.getTaxProfile(userId));

    }

    @GetMapping("/profile/{userId}/{financialYear}")

    public ResponseEntity<TaxProfileResponse> getTaxProfileByYear(

            @PathVariable Long userId,

            @PathVariable String financialYear) {

        return ResponseEntity.ok(

                taxDataService.getTaxProfileByYear(

                        userId, financialYear));

    }

    @GetMapping("/summary/{userId}")

    public ResponseEntity<TaxSummaryResponse> getTaxSummary(

            @PathVariable Long userId) {

        return ResponseEntity.ok(

                taxDataService.getTaxSummary(userId));

    }

    @GetMapping("/profiles/{userId}/all")

    public ResponseEntity<List<TaxProfileResponse>> getAllProfiles(

            @PathVariable Long userId) {

        return ResponseEntity.ok(

                taxDataService.getAllTaxProfiles(userId));

    }

}

