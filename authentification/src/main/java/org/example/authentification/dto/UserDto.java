
package org.example.authentification.dto;

import lombok.Builder;
import lombok.Data;
import org.example.authentification.entites.Role;
import org.example.authentification.entites.User;


@Data
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private String phone;
    private String password;
    private Role role;
    private boolean isEnabled;

    public static UserDto fromEntity(User request) {

        return UserDto.builder()
                .id(request.getId())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .role(request.getRole())
                .phone(request.getPhone())
                .isEnabled(request.getIsEnabled())
                .build();
    }



   /* public static User toEntity(UserDtoo dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setPhone(dto.getPhone());

        return user;
    }*/
}
