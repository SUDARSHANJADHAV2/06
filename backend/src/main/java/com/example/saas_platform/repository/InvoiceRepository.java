package com.example.saas_platform.repository;

import com.example.saas_platform.model.Invoice;
import com.example.saas_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByUser(User user);
}
