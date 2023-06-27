package com.axing.reggie.service.impl;

import com.axing.reggie.domain.AddressBook;
import com.axing.reggie.domain.User;
import com.axing.reggie.mapper.AddressBookMapper;
import com.axing.reggie.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 查询地址簿信息列表
     * @return
     */
    @Override
    public List<AddressBook> getList(Long userId) {
        return addressBookMapper.getAddressBookList(userId);
    }

    /**
     * 新增地址
     * @param addressBook
     */
    @Override
    public void saveAddress(AddressBook addressBook) {
        addressBookMapper.saveAddressBook(addressBook);
    }

    /**
     * 修改默认地址
     * @param addressBook
     */
    @Override
    @Transactional
    public void setDefault(AddressBook addressBook) {
        //将当前用户的地址全部设为不默认
        addressBookMapper.setAddressNoDefault(addressBook);
        //将当前用户地址其中一个设为默认
        addressBookMapper.setAddressDefault(addressBook);
    }

    /**
     * 获取单个地址簿
     * @param id
     * @return
     */
    @Override
    public AddressBook getAddressBook(Long id) {
        return addressBookMapper.getAddressBookById(id);
    }

    /**
     * 修改地址薄
     * @param addressBook
     */
    @Override
    public void updateAddressBook(AddressBook addressBook) {
        addressBookMapper.updateAddressBookById(addressBook);
    }

    /**
     * 删除收货地址
     * @param id
     */
    @Override
    public void deleteAddress(Long id) {
        addressBookMapper.deleteAddressById(id);
    }

    /**
     * 根据当前用户id获取默认地址
     * @param userId
     * @return
     */
    @Override
    public AddressBook getDefaultByUserId(Long userId) {
        return addressBookMapper.getDefaultByUserId(userId);
    }
}
