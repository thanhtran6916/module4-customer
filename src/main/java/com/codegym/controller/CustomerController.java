package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private Customer customer;

    @GetMapping("/customers")
    public String showList(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "/customers/list";
    }

    @GetMapping("/customers/{id}")
    public String information(Model model, @PathVariable Long id) {
        customer = customerService.findOne(id);
        model.addAttribute("customer", customer);
        return "/customers/info";
    }

    @PostMapping("/customers/{id}")
    public ModelAndView edit(@PathVariable Long id,
                             @RequestParam("name") String name,
                             @RequestParam("email") String email,
                             @RequestParam("address") String address) {
        customer = customerService.findOne(id);
        customer.setName(name);
        customer.setEmail(email);
        customer.setAddress(address);
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/customers/list");
        modelAndView.addObject("customers", customerService.findAll());
        return modelAndView;
    }

}
