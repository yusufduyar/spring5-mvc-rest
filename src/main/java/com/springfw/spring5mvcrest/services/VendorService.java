package com.springfw.spring5mvcrest.services;

import com.springfw.spring5mvcrest.api.v1.mapper.VendorMapper;
import com.springfw.spring5mvcrest.api.v1.model.VendorDTO;
import com.springfw.spring5mvcrest.api.v1.model.VendorListDTO;
import com.springfw.spring5mvcrest.domain.Vendor;
import com.springfw.spring5mvcrest.repositories.IVendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VendorService implements IVendorService {
    private final VendorMapper vendorMapper;
    private final IVendorRepository vendorRepository;

    public VendorService(VendorMapper vendorMapper, IVendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public VendorListDTO getAllVendors() {
        List<VendorDTO> vendorList = vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl("/api/v1/vendors/" + vendor.getId());
                    return vendorDTO;
                }).collect(Collectors.toList());
        VendorListDTO vendorListDTO = new VendorListDTO();
        vendorListDTO.setVendors(vendorList);

        return vendorListDTO;
    }
}
