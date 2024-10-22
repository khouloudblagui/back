package org.example.authentification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/nurse")
@Tag(name = "nurse")
@CrossOrigin(origins = "*")
public class NurseController {

    @Operation(
            description = "Get endpoint for nurse",
            summary = "This is a summary for nurse get endpoint",
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
        return "GET:: nurse controller";
    }
    @PostMapping
    public String post(){
        return "POST:: nurse controller";
    }
    @PutMapping
    public String put(){
        return "PUT:: nurse controller";
    }
    @DeleteMapping
    public String delete(){
        return "DELETE:: nurse controller";
    }
}