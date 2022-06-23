package org.example.model;

import lombok.Data;

import java.util.List;

@Data
public class AddressInputDto
{
    private String city;
    private String country;
    private String zipCode;
    private String addressLine;
    private List<String> userIds;
}
