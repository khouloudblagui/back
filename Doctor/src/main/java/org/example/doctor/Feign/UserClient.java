package org.example.doctor.Feign;

import org.example.authentification.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service")
public interface UserClient {
    @GetMapping("/api/v1/users/{userId}")
    UserDto getUserById(@PathVariable("userId") Long userId);
}
