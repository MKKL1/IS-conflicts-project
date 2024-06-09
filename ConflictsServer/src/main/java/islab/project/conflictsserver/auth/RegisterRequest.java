package islab.project.conflictsserver.auth;

import islab.project.conflictsserver.validators.UniqueUsername;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotEmpty
    @UniqueUsername
    private String username;
    @NotEmpty
    private String password;

}
