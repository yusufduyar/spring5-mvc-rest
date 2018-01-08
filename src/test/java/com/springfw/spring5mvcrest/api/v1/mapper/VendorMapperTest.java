package com.springfw.spring5mvcrest.api.v1.mapper;

import com.springfw.spring5mvcrest.api.v1.model.VendorDTO;
import com.springfw.spring5mvcrest.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendorMapperTest {
    public static final String NAME = "name";

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDTO() throws Exception {
        //given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);

        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        //then
        assertEquals(vendor.getName(),vendorDTO.getName());
    }

    @Test
    public void vendorDTOtoVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        //when
        Vendor vendor = vendorMapper.VendorDTOtoVendor(vendorDTO);

        //then
        assertEquals(vendorDTO.getName(),vendor.getName());
    }
}
