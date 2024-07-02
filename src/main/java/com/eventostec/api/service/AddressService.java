package com.eventostec.api.service;

import com.eventostec.api.domain.address.Address;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventRequestDTO;
import com.eventostec.api.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public void createAddress(EventRequestDTO data, Event event) {
        Address newAddress = new Address();

        newAddress.setCity(data.city());
        newAddress.setUf(data.uf());
        newAddress.setEvent(event);

        addressRepository.save(newAddress);
    }
}
