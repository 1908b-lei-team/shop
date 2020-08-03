package com.fh.address.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.common.ServerResponse;
import com.fh.address.mapper.AddressMapper;
import com.fh.address.model.Address;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    private AddressMapper addressMapper;

    @Override
    public ServerResponse queryAddressList() {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("status");
        List<Address> list = addressMapper.selectList(queryWrapper);
        return ServerResponse.success(list);
    }

    @Override
    public void del(Integer id) {
        addressMapper.deleteById(id);
    }

    @Override
    public void upd(Address address) {
        addressMapper.updateById(address);
    }

    @Override
    public void updStatus(Integer id) {
        List<Address> list = addressMapper.selectList(null);
        for (Address address : list) {
            address.setStatus(0);
            addressMapper.updateById(address);
        }
        Address address = addressMapper.selectById(id);
        address.setStatus(1);
        addressMapper.updateById(address);
    }

    @Override
    public Address selectOne(Integer addressId) {
        return addressMapper.selectById(addressId);
    }


}
