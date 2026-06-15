package com.taxbuddy.taxbuddy_ai_chat_service.ingestion;


import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.document.Document;

import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;

import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;

import java.util.List;

import java.util.Map;

@Component

@RequiredArgsConstructor

@Slf4j

public class TaxKnowledgeIngestion implements CommandLineRunner {

    private final VectorStore vectorStore;


    @Override
    public void run(String... args) {
        log.info("Checking tax knowledge base...");

        try {
            SearchRequest searchRequest = SearchRequest.builder()
                    .query("Section 80C")
                    .topK(1).build();

            List<Document> existing =
                    vectorStore.similaritySearch(searchRequest);

            if (!existing.isEmpty()) {
                log.info("Tax knowledge already ingested. " +
                        "Skipping ingestion.");
                return;
            }
        } catch (Exception e) {
            log.warn("Could not check existing documents. " +
                    "Proceeding with ingestion.");
        }

        log.info("Starting tax knowledge ingestion...");
        ingestTaxDocuments();
        log.info("Tax knowledge ingestion completed.");
    }

    private void ingestTaxDocuments() {

        List<Document> documents = List.of(

                new Document("""

                        Section 80C of Income Tax Act allows individuals

                        and HUFs to claim deductions up to ₹1,50,000

                        per financial year. Eligible investments include:

                        - PPF (Public Provident Fund)

                        - ELSS Mutual Funds (Equity Linked Saving Scheme)

                        - Life Insurance Premium

                        - EPF (Employee Provident Fund)

                        - NSC (National Savings Certificate)

                        - SCSS (Senior Citizen Savings Scheme)

                        - Home loan principal repayment

                        - Children's tuition fees (up to 2 children)

                        - Sukanya Samriddhi Account

                        - Tax Saving Fixed Deposits (5 year lock-in)

                        Maximum deduction limit: ₹1,50,000 per year.

                        """,

                        Map.of("section", "80C",

                                "category", "deductions")),

                new Document("""

                        Section 80D allows deduction for health insurance

                        premiums paid for self, spouse, children and parents.

                        Deduction limits for FY 2024-25:

                        - Self and family (below 60 years): ₹25,000

                        - Self and family (senior citizen 60+): ₹50,000

                        - Parents (below 60 years): additional ₹25,000

                        - Parents (senior citizen 60+): additional ₹50,000

                        Maximum total deduction possible: ₹1,00,000

                        (₹50,000 for self + ₹50,000 for senior parent)

                        Preventive health checkup: ₹5,000 included

                        within the above limits.

                        """,

                        Map.of("section", "80D",

                                "category", "deductions")),

                new Document("""

                        HRA (House Rent Allowance) Exemption Calculation.

                        HRA exemption is minimum of these three:

                        1. Actual HRA received from employer

                        2. 50% of basic salary (metro cities)

                           40% of basic salary (non-metro cities)

                        3. Actual rent paid minus 10% of basic salary

                        Metro cities: Mumbai, Delhi, Chennai, Kolkata

                        Non-metro: All other cities

                        Important conditions:

                        - Must be paying rent for accommodation

                        - Cannot claim if living in own house

                        - Rent receipt required for rent > ₹1 lakh/year

                        - Landlord PAN required if rent > ₹1 lakh/year

                        HRA is only available under OLD tax regime.

                        Under NEW tax regime HRA exemption is not allowed.

                        """,

                        Map.of("section", "HRA",

                                "category", "exemptions")),

                new Document("""

                        New Tax Regime vs Old Tax Regime FY 2024-25.

                        New Tax Regime Tax Slabs (Default from FY 2023-24):

                        - Up to ₹3,00,000: NIL

                        - ₹3,00,001 to ₹6,00,000: 5%

                        - ₹6,00,001 to ₹9,00,000: 10%

                        - ₹9,00,001 to ₹12,00,000: 15%

                        - ₹12,00,001 to ₹15,00,000: 20%

                        - Above ₹15,00,000: 30%

                        Old Tax Regime Tax Slabs:

                        - Up to ₹2,50,000: NIL

                        - ₹2,50,001 to ₹5,00,000: 5%

                        - ₹5,00,001 to ₹10,00,000: 20%

                        - Above ₹10,00,000: 30%

                        New Regime benefits:

                        - Lower tax rates

                        - Standard deduction ₹75,000 allowed

                        - No need to maintain investment proofs

                        Old Regime benefits:

                        - 80C deduction ₹1,50,000

                        - 80D deduction ₹25,000-₹1,00,000

                        - HRA exemption

                        - Home loan interest deduction

                        - Many other deductions

                        Choose old regime if total deductions > ₹3,75,000

                        Choose new regime if total deductions < ₹3,75,000

                        """,

                        Map.of("section", "tax-regime",

                                "category", "general")),

                new Document("""

                        ITR Filing Types for Individuals FY 2024-25:

                        ITR-1 (Sahaj) - For salaried individuals:

                        - Income from salary/pension

                        - One house property

                        - Other sources (interest, dividends)

                        - Total income up to ₹50 lakhs

                        - Cannot use if foreign assets or income

                        ITR-2 - For individuals with capital gains:

                        - Salary income

                        - Multiple house properties

                        - Capital gains from shares, mutual funds

                        - Foreign assets or income

                        - Income above ₹50 lakhs

                        ITR-3 - For business/profession income:

                        - Proprietorship business

                        - Professional income

                        - Partnership firm income

                        ITR-4 (Sugam) - For presumptive taxation:

                        - Business income under 44AD

                        - Professional income under 44ADA

                        - Income up to ₹50 lakhs

                        Due date for filing ITR: July 31st each year

                        Belated return: December 31st with penalty

                        """,

                        Map.of("section", "ITR",

                                "category", "filing")),

                new Document("""

                        TDS (Tax Deducted at Source) and Refund Process:

                        TDS is tax deducted by employer before paying salary.

                        Form 26AS shows all TDS deducted against your PAN.

                        How TDS refund works:

                        1. Employer deducts TDS based on estimated tax

                        2. You file ITR with actual income and deductions

                        3. If TDS deducted > actual tax payable = REFUND

                        4. If TDS deducted < actual tax payable = PAY MORE

                        Refund timeline:

                        - Usually 20-45 days after ITR filing

                        - Must verify ITR within 30 days of filing

                        - Refund credited to bank account linked with PAN

                        To check refund status:

                        - Visit incometax.gov.in

                        - Login with PAN and password

                        - Check 'Refund/Demand Status'

                        If refund not received after 3 months:

                        - Raise grievance on income tax portal

                        - Check if bank account is pre-validated

                        """,

                        Map.of("section", "TDS",

                                "category", "refund")),

                new Document("""

                        Standard Deduction for Salaried Employees:

                        FY 2024-25 standard deduction:

                        - Old Tax Regime: ₹50,000

                        - New Tax Regime: ₹75,000

                        Standard deduction is automatic.

                        No proof or investment needed.

                        Directly subtracted from gross salary.

                        Example calculation:

                        Gross Salary          : ₹12,00,000

                        Standard Deduction    : ₹75,000 (new regime)

                        Net Salary after SD   : ₹11,25,000

                        Standard deduction replaced:

                        - Transport allowance (₹19,200)

                        - Medical reimbursement (₹15,000)

                        Both were previously separate exemptions.

                        """,

                        Map.of("section", "standard-deduction",

                                "category", "deductions")),

                new Document("""

                        Home Loan Tax Benefits:

                        Section 24(b) - Interest on Home Loan:

                        - Self occupied property: up to ₹2,00,000

                        - Let out property: actual interest (no limit)

                        - Available in OLD regime only

                        - Pre-construction interest: 1/5th per year

                          for 5 years after possession

                        Section 80C - Principal Repayment:

                        - Up to ₹1,50,000 (within 80C limit)

                        - Available in OLD regime only

                        Section 80EEA - Additional interest deduction:

                        - For first time home buyers

                        - Loan sanctioned between 01/04/2019-31/03/2022

                        - Property value up to ₹45 lakhs

                        - Additional ₹1,50,000 over 24(b) limit

                        Under NEW tax regime:

                        - No deduction for self occupied property

                        - Let out property interest still deductible

                        """,

                        Map.of("section", "home-loan",

                                "category", "deductions")),

                new Document("""

                        Capital Gains Tax for FY 2024-25:

                        Short Term Capital Gains (STCG):

                        - Equity shares/mutual funds held < 12 months

                        - Tax rate: 20% (increased from 15% in budget 2024)

                        Long Term Capital Gains (LTCG):

                        - Equity shares/mutual funds held > 12 months

                        - Exempt up to ₹1,25,000 per year

                        - Above ₹1,25,000: taxed at 12.5%

                        - No indexation benefit for equity

                        Debt Mutual Funds (from April 2023):

                        - Always taxed as per income tax slab

                        - No LTCG benefit regardless of holding period

                        Property Capital Gains:

                        - Short term: held < 24 months, taxed at slab rate

                        - Long term: held > 24 months, taxed at 12.5%

                        - Indexation benefit removed from budget 2024

                        """,

                        Map.of("section", "capital-gains",

                                "category", "investments")),

                new Document("""

                        Income Tax Filing Checklist for FY 2024-25:

                        Documents needed:

                        1. Form 16 from employer (Part A and Part B)

                        2. Form 26AS (Tax Credit Statement)

                        3. AIS (Annual Information Statement)

                        4. Bank statements for interest income

                        5. Investment proofs for 80C deductions

                        6. Health insurance premium receipts (80D)

                        7. Rent receipts if claiming HRA

                        8. Home loan certificate from bank

                        9. Capital gains statement from broker

                        10. Aadhaar and PAN card

                        Steps to file ITR:

                        1. Visit incometax.gov.in

                        2. Login with PAN

                        3. Select correct ITR form

                        4. Pre-fill data from Form 16 and AIS

                        5. Verify all pre-filled data

                        6. Add any missing income or deductions

                        7. Calculate tax payable

                        8. Pay any additional tax (if applicable)

                        9. Submit ITR

                        10. E-verify within 30 days

                        """,

                        Map.of("section", "filing-checklist",

                                "category", "filing"))

        );

        try {

            vectorStore.add(documents);

            log.info("Successfully ingested {} tax documents " +

                    "into Qdrant", documents.size());

        } catch (Exception e) {

            log.error("Failed to ingest tax documents", e);

        }

    }

}

