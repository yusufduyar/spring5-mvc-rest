package com.springfw.spring5mvcrest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {
    public String name;
    @JsonProperty("vendor_url")
    public String vendorUrl;
}
