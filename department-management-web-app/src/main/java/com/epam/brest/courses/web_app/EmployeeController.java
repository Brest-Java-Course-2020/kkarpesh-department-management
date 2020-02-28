package com.epam.brest.courses.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {

    @GetMapping(value = "employees")
    private final String employees(Model model) {
        return "employees";
    }

    @GetMapping(value = "/employee")
    public final String employee() {
        return "employee";
    }
}