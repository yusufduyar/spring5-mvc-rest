package com.springfw.spring5mvcrest.services;

import com.springfw.spring5mvcrest.api.v1.mapper.VendorMapper;
import com.springfw.spring5mvcrest.api.v1.model.VendorDTO;
import com.springfw.spring5mvcrest.api.v1.model.VendorListDTO;
import com.springfw.spring5mvcrest.controllers.v1.VendorController;
import com.springfw.spring5mvcrest.domain.Vendor;
import com.springfw.spring5mvcrest.repositories.IVendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VendorServiceTest {
    public static final String VENDOR_NAME = "Dominos";
    public static final long ID = 1L;
    IVendorService vendorService;

    @Mock
    IVendorRepository vendorRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorService(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void getAllVendors() throws Exception {
        //given
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor());
        given(vendorRepository.findAll()).willReturn(vendors);

        //when
        VendorListDTO vendorList = vendorService.getAllVendors();

        //then
        then(vendorRepository).should(times(1)).findAll();
        assertThat(vendorList.getVendors().size(),is(equalTo(2)));
    }

    @Test
    public void getVendorByIdTest() throws Exception {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(VENDOR_NAME);

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(ID);

        //then
        then(vendorRepository).should(times(1)).findById(anyLong());

        assertThat(vendor.getName(), is(equalTo(vendorDTO.getName())));
    }

    @Test
    public void createNewVendorTest() throws Exception {
        Vendor vendor = new Vendor();
        vendor.setName(VENDOR_NAME);
        vendor.setId(ID);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO newVendorDTO = new VendorDTO();
        newVendorDTO.setName(vendor.getName());

        VendorDTO savedDto = vendorService.createNewVendor(newVendorDTO);

        assertEquals(vendor.getName(), savedDto.getName());
        assertEquals(savedDto.getVendorUrl(), getVendorURL(vendor.getId()));
    }

    @Test
    public void updateVendorTest() throws Exception {
        //given
        VendorDTO request = new VendorDTO();
        request.setName("New Vendor Name");

        Vendor vendor = new Vendor();
        vendor.setName(request.getName());
        vendor.setId(ID);

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(vendor));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        //when
        VendorDTO result = vendorService.updateVendor(ID, request);

        assertEquals(result.getName(), request.getName());
        assertEquals(result.getVendorUrl(),getVendorURL(vendor.getId()));
    }

    @Test
    public void patchVendorTest() throws Exception {
        //given
        VendorDTO request = new VendorDTO();
        request.setName("New Vendor Name");

        Vendor vendor = new Vendor();
        vendor.setName(request.getName());
        vendor.setId(ID);

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(vendor));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        //when
        VendorDTO result = vendorService.patchVendor(ID, request);

        assertEquals(result.getName(), request.getName());
        assertEquals(result.getVendorUrl(),getVendorURL(vendor.getId()));
    }

    @Test
    public void deleteVendorTest() throws Exception {

        vendorService.deleteVendorById(ID);

        verify(vendorRepository,times(1)).deleteById(anyLong());
    }

    private String getVendorURL(Long id) {
        return VendorController.BASE_URL + "/" + id;
    }
}