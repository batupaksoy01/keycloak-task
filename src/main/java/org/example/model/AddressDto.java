package org.example.model;

import lombok.Data;

@Data
public class AddressDto
{
    private String city;
    private String country;
    private String zipCode;
    private String addressLine;
}
