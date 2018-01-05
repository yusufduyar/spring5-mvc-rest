package com.springfw.spring5mvcrest.repositories;

import com.springfw.spring5mvcrest.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVendorRepository extends JpaRepository<Vendor,Long> {
}
