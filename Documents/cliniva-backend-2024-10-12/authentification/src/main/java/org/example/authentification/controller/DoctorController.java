package org.example.authentification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/doctor")
@Tag(name = "doctor")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Operation(
            description = "Get endpoint for doctor",
            summary = "This is a summary for doctor get endpoint",
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
        return "GET:: doctor controller";
    }
    @PostMapping
    public String post(){
        return "POST:: doctor controller";
    }
    @PutMapping
    public String put(){
        return "PUT:: doctor controller";
    }
    @DeleteMapping
    public String delete(){
        return "DELETE:: doctor controller";
    }
}


