package com.taxbuddy.taxbuddy_ai_chat_service.tools;



import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.tool.annotation.Tool;

import org.springframework.ai.tool.annotation.ToolParam;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import org.springframework.web.client.RestClient;

@Component

@RequiredArgsConstructor

@Slf4j

public class TaxPrefillTools {

    @Value("${taxbuddy.services.tax-data-url}")

    private String taxDataUrl;

    @Tool(description = "Fetches the user's tax summary including " +

            "gross salary, taxable income, tax payable, TDS paid, " +

            "and refund or due amount. Use this whenever the user " +

            "asks about their refund, tax owed, or tax summary.")

    public String getTaxSummary(

            @ToolParam(description = "The numeric user ID")

            Long userId) {

        log.info("TOOL CALLED: getTaxSummary userId={}", userId);

        try {

            RestClient restClient = RestClient.create();

            String response = restClient.get()

                    .uri(taxDataUrl + "/tax/summary/" + userId)

                    .retrieve()

                    .body(String.class);

            log.info("TOOL RESULT: {}", response);

            return response;

        } catch (Exception e) {

            log.error("Tool failed", e);

            return "Unable to fetch tax summary.";

        }

    }

    @Tool(description = "Fetches the complete tax profile for a user " +

            "including salary breakdown, HRA, Section 80C, Section 80D, " +

            "taxable income, tax payable, TDS, regime and filing status. " +

            "Use when user asks about detailed tax info or deductions.")

    public String getTaxProfile(

            @ToolParam(description = "The numeric user ID")

            Long userId) {

        log.info("TOOL CALLED: getTaxProfile userId={}", userId);

        try {

            RestClient restClient = RestClient.create();

            String response = restClient.get()

                    .uri(taxDataUrl + "/tax/profile/" + userId)

                    .retrieve()

                    .body(String.class);

            log.info("TOOL RESULT: {}", response);

            return response;

        } catch (Exception e) {

            log.error("Tool failed", e);

            return "Unable to fetch tax profile.";

        }

    }


    @PostConstruct
    public void init(){
        log.info("====TaxPrefillTools been created ===");
    }
}
