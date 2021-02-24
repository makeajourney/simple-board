package kr.makeajourney.board.web;

import kr.makeajourney.board.service.JwtService;
import kr.makeajourney.board.web.model.AuthRequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthApiController {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity generateToken(@RequestBody AuthRequestModel request) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = (User) auth.getPrincipal();

        return ResponseEntity.ok(jwtService.generateToken(user.getUsername(), user.getAuthorities()));
    }

}
