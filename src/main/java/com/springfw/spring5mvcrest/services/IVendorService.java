package com.springfw.spring5mvcrest.services;

import com.springfw.spring5mvcrest.api.v1.model.VendorDTO;
import com.springfw.spring5mvcrest.api.v1.model.VendorListDTO;

public interface IVendorService{
    VendorListDTO getAllVendors();
    VendorDTO getVendorById(Long id);
    VendorDTO createNewVendor(VendorDTO vendorDTO);
    VendorDTO updateVendor(Long id,VendorDTO vendorDTO);
    VendorDTO patchVendor(Long id,VendorDTO vendorDTO);
    void deleteVendorById(Long id);
}
