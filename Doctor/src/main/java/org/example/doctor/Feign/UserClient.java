package org.example.doctor.Feign;

import org.example.authentification.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service")
public interface UserClient {
    @GetMapping("/api/v1/users/{userId}")
    UserDto getUserById(@PathVariable("userId") Long userId);

    @GetMapping("/api/v1/auth/validate-token/{token}")
    Boolean validateToken(@PathVariable("token") String token);

    @GetMapping("/api/v1/auth/user-by-token/{token}")
    UserDto getUserByToken(@PathVariable("token") String token);
}
