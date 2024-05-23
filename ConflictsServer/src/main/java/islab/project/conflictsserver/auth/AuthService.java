package islab.project.conflictsserver.auth;

import islab.project.conflictsserver.user.Role;
import islab.project.conflictsserver.user.User;
import islab.project.conflictsserver.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    // jak daje z tokenem żądania to sie wysypuje
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User
                .builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = repository.findByUsername(request.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
