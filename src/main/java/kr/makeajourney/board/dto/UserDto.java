package kr.makeajourney.board.dto;

import kr.makeajourney.board.domain.user.Role;
import kr.makeajourney.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {
    private Long id;
    private String email;
    private Role role;

    @Builder
    public UserDto(Long id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public UserDto(User entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.role = entity.getRole();
    }

    public String getName() {
        return email;
    }
}
