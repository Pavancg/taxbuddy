package com.taxbuddy.taxbuddy_tax_data_service.dto;


import com.taxbuddy.taxbuddy_tax_data_service.entity.FilingStatus;
import com.taxbuddy.taxbuddy_tax_data_service.entity.TaxProfile;
import com.taxbuddy.taxbuddy_tax_data_service.entity.TaxRegime;

import java.math.BigDecimal;

public record TaxSummaryResponse(

        Long userId,

        String fullName,

        String financialYear,

        BigDecimal grossSalary,

        BigDecimal taxableIncome,

        BigDecimal taxPayable,

        BigDecimal tdsPaid,

        BigDecimal refundOrDue,

        TaxRegime taxRegime,

        FilingStatus filingStatus

) {

    public static TaxSummaryResponse fromEntity(

            TaxProfile profile) {

        return new TaxSummaryResponse(

                profile.getUserId(),

                profile.getFullName(),

                profile.getFinancialYear(),

                profile.getGrossSalary(),

                profile.getTaxableIncome(),

                profile.getTaxPayable(),

                profile.getTdsPaid(),

                profile.getRefundOrDue(),

                profile.getTaxRegime(),

                profile.getFilingStatus()

        );

    }

}

