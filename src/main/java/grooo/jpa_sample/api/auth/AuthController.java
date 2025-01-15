package grooo.jpa_sample.api.auth;

import grooo.jpa_sample.api.auth.dto.LoginRequest;
import grooo.jpa_sample.api.auth.dto.SignupRequest;
import grooo.jpa_sample.api.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        request.basicValidate();
        String token = authService.login(request);
        return ResponseEntity.ok().body("Bearer " + token);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        request.basicValidate();
        authService.signup(request);
        return ResponseEntity.ok().build();
    }
}
