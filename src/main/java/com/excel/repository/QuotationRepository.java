package com.excel.repository;

import com.excel.model.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuotationRepository extends JpaRepository<Quotation, Long> {

    List<Quotation> findByGysContains(String gys);

    List<Quotation> findByGys(String gys);

    List<Quotation> findByDhAndAndSfcj(String dh,String sfcj);
}
