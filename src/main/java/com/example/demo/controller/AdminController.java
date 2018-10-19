package com.example.demo.controller;

import com.example.demo.model.Brand;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.BrandRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class AdminController {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Value("${demojk.product.upload.path}")
    private String imageUploadPath;

    @GetMapping("/admin")
    public String adminPage(ModelMap map) {
        map.addAttribute("category", new Category());
        map.addAttribute("brand", new Brand());
        map.addAttribute("product", new Product());
        map.addAttribute("allCategories", categoryRepository.findAll());
        map.addAttribute("allBrands", brandRepository.findAll());
        return "admin";
    }

    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public String addCategory(@ModelAttribute(name = "category") Category category) {
        categoryRepository.save(category);
        return "redirect:/admin";
    }

    @RequestMapping(value = "add/Brand", method = RequestMethod.POST)
    public String addBrand(@ModelAttribute(name = "brand") Brand brand) {
        brandRepository.save(brand);
        return "redirect:/brand";
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute(name = "product") Product product, @RequestParam(value = "image") MultipartFile file) throws IOException {
        File dir = new File(imageUploadPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String picName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        File picture = new File(imageUploadPath + picName);
        file.transferTo(picture);
        product.setPicUrl(picName);
        productRepository.save(product);
        return "redirect:/product";
    }

    @RequestMapping(value = "/product/image", method = RequestMethod.GET)
    public void getImageAsByteArray(HttpServletResponse response, @RequestParam("fileName") String fileName) throws IOException {
        InputStream in = new FileInputStream(imageUploadPath + fileName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }


}