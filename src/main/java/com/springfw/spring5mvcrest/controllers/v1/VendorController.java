package com.springfw.spring5mvcrest.controllers.v1;

import com.springfw.spring5mvcrest.api.v1.model.VendorDTO;
import com.springfw.spring5mvcrest.api.v1.model.VendorListDTO;
import com.springfw.spring5mvcrest.services.IVendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
    public static final String BASE_URL = "/api/v1/vendors";

    private final IVendorService vendorService;

    public VendorController(IVendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors() throws Exception{
        return vendorService.getAllVendors();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id) throws Exception {
        return vendorService.getVendorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO){
        return vendorService.createNewVendor(vendorDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable Long id,@RequestBody VendorDTO vendorDTO){
        return vendorService.updateVendor(id,vendorDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable Long id,@RequestBody VendorDTO vendorDTO){
        return vendorService.patchVendor(id,vendorDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendorById(@PathVariable Long id){
        vendorService.deleteVendorById(id);
    }
}
