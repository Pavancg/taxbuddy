package com.taxbuddy.taxbuddy_tax_data_service.dto;


import com.taxbuddy.taxbuddy_tax_data_service.entity.FilingStatus;
import com.taxbuddy.taxbuddy_tax_data_service.entity.TaxProfile;
import com.taxbuddy.taxbuddy_tax_data_service.entity.TaxRegime;

import java.math.BigDecimal;

public record TaxProfileResponse(

        Long userId,

        String fullName,

        String panNumber,

        String financialYear,

        BigDecimal grossSalary,

        BigDecimal basicSalary,

        BigDecimal hra,

        BigDecimal standardDeduction,

        BigDecimal section80C,

        BigDecimal section80D,

        BigDecimal taxableIncome,

        BigDecimal taxPayable,

        BigDecimal tdsPaid,

        BigDecimal refundOrDue,

        TaxRegime taxRegime,

        FilingStatus filingStatus

) {

    public static TaxProfileResponse fromEntity(

            TaxProfile profile) {

        return new TaxProfileResponse(

                profile.getUserId(),

                profile.getFullName(),

                profile.getPanNumber(),

                profile.getFinancialYear(),

                profile.getGrossSalary(),

                profile.getBasicSalary(),

                profile.getHra(),

                profile.getStandardDeduction(),

                profile.getSection80C(),

                profile.getSection80D(),

                profile.getTaxableIncome(),

                profile.getTaxPayable(),

                profile.getTdsPaid(),

                profile.getRefundOrDue(),

                profile.getTaxRegime(),

                profile.getFilingStatus()

        );

    }

}

