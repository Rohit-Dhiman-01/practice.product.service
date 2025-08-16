package com.ecommerce.product.service.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Tag(name = "Home Controller")
public class HomeController {
    @RequestMapping("/api/v1/")
    public String index(){
        return "index.html";
    }
}
