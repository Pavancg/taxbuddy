package com.taxbuddy.taxbuddy_tax_data_service.repository;

import com.taxbuddy.taxbuddy_tax_data_service.entity.TaxProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaxProfileRepository extends JpaRepository<TaxProfile, Long> {

    Optional<TaxProfile> findByUserId(Long userId);

    Optional<TaxProfile> findByUserIdAndFinancialYear(Long userId, String financialYear);

    List<TaxProfile> findAllByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
