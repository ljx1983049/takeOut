package com.axing.reggie.service;

import com.axing.reggie.domain.AddressBook;

import java.util.List;

public interface AddressBookService {
    List<AddressBook> getList(Long userId);

    void saveAddress(AddressBook addressBook);

    void setDefault(AddressBook addressBook);

    AddressBook getAddressBook(Long id);

    void updateAddressBook(AddressBook addressBook);

    void deleteAddress(Long id);

    AddressBook getDefaultByUserId(Long userId);

}
