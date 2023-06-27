package com.axing.reggie.mapper;

import com.axing.reggie.domain.AddressBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {

    /**
     * 查询地址簿信息列表
     * @return
     */
    List<AddressBook> getAddressBookList(Long userId);

    /**
     * 新增收货地址薄
     * @param addressBook
     */
    void saveAddressBook(AddressBook addressBook);

    /**
     * 将当前用户的地址全部设为不默认
     * @param addressBook
     */
    void setAddressNoDefault(AddressBook addressBook);

    /**
     * 将当前用户地址其中一个设为默认
     * @param addressBook
     */
    void setAddressDefault(AddressBook addressBook);

    /**
     * 根据id获取地址薄
     * @param id
     * @return
     */
    AddressBook getAddressBookById(Long id);

    /**
     * 根据id修改地址簿信息
     * @param addressBook
     */
    void updateAddressBookById(AddressBook addressBook);

    /**
     * 根据id删除收货地址薄
     * @param id
     */
    void deleteAddressById(Long id);

    /**
     * 根据当前用户id获取用户默认地址
     * @param userId
     * @return
     */
    AddressBook getDefaultByUserId(Long userId);
}
