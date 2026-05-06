package com.srotip.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "Invalid Email")
    @NotBlank(message = "Email is required")
    private String email;
    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}