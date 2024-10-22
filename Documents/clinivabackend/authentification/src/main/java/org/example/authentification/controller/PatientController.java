package org.example.authentification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/patient")
@Tag(name = "patient")
@CrossOrigin(origins = "*")
public class PatientController {

    @Operation(
            description = "Get endpoint for patient",
            summary = "This is a summary for patient get endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )

    @GetMapping
    public String get(){
        return "GET:: patient controller";
    }
    @PostMapping
    public String post(){
        return "POST:: patient controller";
    }
    @PutMapping
    public String put(){
        return "PUT:: patient controller";
    }
    @DeleteMapping
    public String delete(){
        return "DELETE:: patient controller";
    }
}
