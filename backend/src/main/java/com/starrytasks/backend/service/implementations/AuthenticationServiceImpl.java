package com.starrytasks.backend.service.implementations;

import com.starrytasks.backend.api.external.*;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.api.internal.UserProfile;
import com.starrytasks.backend.mapper.UserMapper;
import com.starrytasks.backend.repository.RoleRepository;
import com.starrytasks.backend.repository.UserRepository;
import com.starrytasks.backend.service.AuthenticationService;
import com.starrytasks.backend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists!");
        }

        UserProfile userProfile = new UserProfile();
        userProfile.setName(request.getFirstName());
        userProfile.setProfilePicturePath(request.getProfilePicturePath());

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserProfile(userProfile);

        userProfile.setUser(user);
        userRepository.save(user);

        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }


    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole() != null ? user.getRole().getName() : null);
        extraClaims.put("hasParent", user.getParent() != null);

        var jwt = jwtService.generateToken(extraClaims, user);
        boolean hasRole = user.getRole() != null;
        return AuthenticationResponse.builder()
                .token(jwt)
                .hasRole(hasRole)
                .build();
    }

    @Override
    public UserDTO verify() {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userMapper.map(user);
    }
}