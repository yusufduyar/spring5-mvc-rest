package com.springfw.spring5mvcrest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VendorDTO {
    public String name;
    @JsonProperty("vendor_url")
    public String vendorUrl;
}
