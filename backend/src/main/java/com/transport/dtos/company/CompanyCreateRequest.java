package com.transport.dtos.company;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyCreateRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @Size(max = 50, message = "Registration number cannot exceed 50 characters")
    private String registrationNumber;

    @Size(max = 500, message = "Address cannot exceed 500 characters")
    private String address;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone must be 10-15 digits, optionally starting with +")
    private String phone;

    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;
}
