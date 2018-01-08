package com.springfw.spring5mvcrest.controllers.v1;

import com.springfw.spring5mvcrest.api.v1.model.VendorDTO;
import com.springfw.spring5mvcrest.api.v1.model.VendorListDTO;
import com.springfw.spring5mvcrest.controllers.RestResponseExceptionHandler;
import com.springfw.spring5mvcrest.services.IVendorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static com.springfw.spring5mvcrest.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {VendorController.class})
public class VendorControllerTest {
    public static final String DOMINOS_VENDOR_NAME = "Dominos";
    public static final String MC_DONALDS_VENDOR_NAME = "McDonalds";
    public static final long ID = 1L;

    @MockBean
    IVendorService vendorService;

    @Autowired
    MockMvc mockMvc;

    VendorDTO vendorDTO1;
    VendorDTO vendorDTO2;
    VendorListDTO vendorListDTO;

    @Before
    public void setUp() throws Exception {
        vendorDTO1 = new VendorDTO(MC_DONALDS_VENDOR_NAME, getVendorUrl(1L));
        vendorDTO2 = new VendorDTO(DOMINOS_VENDOR_NAME, getVendorUrl(2L));
        vendorListDTO = new VendorListDTO(Arrays.asList(vendorDTO1, vendorDTO2));
    }

    @Test
    public void getAllVendorsTest() throws Exception {
        given(vendorService.getAllVendors()).willReturn(vendorListDTO);

        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void getVendorByIdTest() throws Exception {
        given(vendorService.getVendorById(anyLong())).willReturn(vendorDTO1);

        mockMvc.perform(get(getVendorUrl(ID))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO1.getName())));
    }

    @Test
    public void createNewVendorTest() throws Exception {
        given(vendorService.createNewVendor(vendorDTO1)).willReturn(vendorDTO1);

        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO1.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(getVendorUrl(ID))));
    }

    @Test
    public void updateVendorTest() throws Exception {
        given(vendorService.updateVendor(anyLong(),any(VendorDTO.class))).willReturn(vendorDTO1);

        mockMvc.perform(put(getVendorUrl(1L))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO1.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorDTO1.getVendorUrl())));
    }

    @Test
    public void patchVendorTest() throws Exception {
        given(vendorService.patchVendor(anyLong(),any(VendorDTO.class))).willReturn(vendorDTO1);

        mockMvc.perform(patch(getVendorUrl(ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO1.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorDTO1.getVendorUrl())));
    }

    @Test
    public void deleteVendorTest() throws Exception {
        mockMvc.perform(delete(getVendorUrl(ID))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(vendorService).should().deleteVendorById(anyLong());
    }

    private String getVendorUrl(Long id) {
        return VendorController.BASE_URL + "/" + id;
    }
}