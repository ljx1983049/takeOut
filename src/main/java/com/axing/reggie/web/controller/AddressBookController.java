package com.axing.reggie.web.controller;

import com.axing.reggie.common.BaseContext;
import com.axing.reggie.common.R;
import com.axing.reggie.domain.AddressBook;
import com.axing.reggie.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 查询地址簿信息列表
     * @return
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list(HttpServletRequest request){
        //获取当前用户id
        Long userId = (Long) request.getSession().getAttribute("userId");
        List<AddressBook> addressBookList = addressBookService.getList(userId);
        return R.success(addressBookList);
    }

    /**
     * 新增收货地址
     * @param addressBook
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody AddressBook addressBook,HttpServletRequest request){
        //获取当前添加地址的用户
        Long userId = (Long) request.getSession().getAttribute("userId");
        addressBook.setUserId(userId);
        addressBookService.saveAddress(addressBook);
        return R.success("添加收货地址成功");
    }

    /**
     * 修改默认地址
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    public R<String> setDefault(@RequestBody AddressBook addressBook, HttpServletRequest request){
        //获取当前用户id
        Long userId = (Long) request.getSession().getAttribute("userId");
        addressBook.setUserId(userId);
        addressBookService.setDefault(addressBook);
        return R.success("修改默认地址成功");
    }

    /**
     * 获取单个地址信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<AddressBook> getAddress(@PathVariable Long id){
        AddressBook addressBook = addressBookService.getAddressBook(id);
        return R.success(addressBook);
    }

    /**
     * 修改地址
     * @param addressBook
     * @return
     */
    @PutMapping
    public R<String> updateAddress(@RequestBody AddressBook addressBook){
        addressBookService.updateAddressBook(addressBook);
        return R.success("修改地址成功");
    }

    /**
     * 删除地址
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids){
        addressBookService.deleteAddress(ids);
        return R.success("删除地址成功");
    }

    /**
     * 获取默认收货地址
     * @return
     */
    @GetMapping("/default")
    public R<AddressBook> getDefault(){
        AddressBook addressBook = addressBookService.getDefaultByUserId(BaseContext.getCurrentId());
        return R.success(addressBook);
    }









}
