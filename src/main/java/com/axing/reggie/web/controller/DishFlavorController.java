package com.axing.reggie.web.controller;

import com.axing.reggie.service.DishFlavorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/dishFlavor")
public class DishFlavorController {

    @Autowired
    private DishFlavorService dishFlavorService;

}
