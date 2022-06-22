package org.example.mapper;

import org.example.model.AddressEntity;
import org.example.model.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper
{
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressEntity addressDtoToAddressEntity(AddressDto addressDto);
}
