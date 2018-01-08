package com.springfw.spring5mvcrest.services;

import com.springfw.spring5mvcrest.api.v1.mapper.VendorMapper;
import com.springfw.spring5mvcrest.api.v1.model.VendorDTO;
import com.springfw.spring5mvcrest.api.v1.model.VendorListDTO;
import com.springfw.spring5mvcrest.controllers.v1.VendorController;
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

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDTO)
                .map(vendorDTO -> {
                    vendorDTO.setVendorUrl(getVendorUrl(id));
                    return vendorDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return saveVendor(vendorMapper.VendorDTOtoVendor(vendorDTO));
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        Vendor vendorByDTO = vendorMapper.VendorDTOtoVendor(vendorDTO);
        vendorByDTO.setId(id);
        return saveVendor(vendorByDTO);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {
            if(vendorDTO.getName() != null)
                vendor.setName(vendorDTO.getName());

            VendorDTO resultDTO = saveVendor(vendor);

            return resultDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    private VendorDTO saveVendor(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO resultDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        resultDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));
        return resultDTO;
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    private String getVendorUrl(Long id){
        return VendorController.BASE_URL+"/"+id;
    }
}
