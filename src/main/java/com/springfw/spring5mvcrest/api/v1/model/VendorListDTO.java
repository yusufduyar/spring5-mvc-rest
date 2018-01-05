package com.springfw.spring5mvcrest.api.v1.model;

import com.springfw.spring5mvcrest.domain.Vendor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorListDTO {
    public List<VendorDTO> vendors;
}
