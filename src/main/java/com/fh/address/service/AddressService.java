package com.fh.address.service;

import com.fh.common.ServerResponse;
import com.fh.address.model.Address;

public interface AddressService {




    ServerResponse queryAddressList();

    void del(Integer id);

    void upd(Address address);

    void updStatus(Integer id);

    Address selectOne(Integer addressId);
}
