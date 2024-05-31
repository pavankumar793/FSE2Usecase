package com.fse2.usecase.userservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.fse2.usecase.userservice.dto.LoginRequest;
import com.fse2.usecase.userservice.dto.UserRegistrationRequest;
import com.fse2.usecase.userservice.entity.User;
import com.fse2.usecase.userservice.impl.MyUserDetailsServiceImpl;
import com.fse2.usecase.userservice.repo.UserRepository;
import com.fse2.usecase.userservice.util.JwtUtil;

@ExtendWith(MockitoExtension.class)
public class MyUserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private MyUserDetailsServiceImpl userService;

    private User user;
    private UserRegistrationRequest registrationRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("username");
        user.setEmail("user@example.com");
        user.setPassword("password");

        registrationRequest = new UserRegistrationRequest();
        registrationRequest.setUsername("newuser");
        registrationRequest.setEmail("newuser@example.com");
        registrationRequest.setPassword("newpassword");

        loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("username");
        loginRequest.setPassword("password");
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        when(userRepository.findByUsername("username")).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("username");

        assertNotNull(userDetails);
        assertEquals("username", userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("username")).thenReturn(null);
        when(userRepository.findByEmail("username")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("username"));
    }

    @Test
    public void testRegister_Success() {
        when(userRepository.existsByEmail("newuser@example.com")).thenReturn(false);
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(encoder.encode("newpassword")).thenReturn("encodedPassword");

        String result = userService.register(registrationRequest);

        assertEquals("User registered successfully!", result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testRegister_EmailAlreadyInUse() {
        when(userRepository.existsByEmail("newuser@example.com")).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> userService.register(registrationRequest));
    }

    @Test
    public void testRegister_UsernameAlreadyInUse() {
        when(userRepository.existsByUsername("newuser")).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> userService.register(registrationRequest));
    }

    @Test
    public void testAuthenticateUser_Success() {
        when(userRepository.findByUsername("username")).thenReturn(user);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("fake_jwt");

        String token = userService.authenticateUser(loginRequest);

        assertEquals("fake_jwt", token);
    }

    @Test
    public void testAuthenticateUser_IncorrectUsernameOrEmail() {
        when(userRepository.findByUsername("username")).thenReturn(null);
        when(userRepository.findByEmail("username")).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> userService.authenticateUser(loginRequest));
    }

    @Test
    public void testAuthenticateUser_IncorrectPassword() {
        when(userRepository.findByUsername("username")).thenReturn(user);
        doThrow(BadCredentialsException.class).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(ResponseStatusException.class, () -> userService.authenticateUser(loginRequest));
    }

    @Test
    public void testLogout_Success() {
        when(request.getSession(false)).thenReturn(session);

        String result = userService.logout(request);

        assertEquals("Logged out successfully", result);
        verify(session, times(1)).invalidate();
    }

    @Test
    public void testLogout_NoActiveSession() {
        when(request.getSession(false)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> userService.logout(request));
    }

    @Test
    public void testLogout_ErrorDuringLogout() {
        when(request.getSession(false)).thenThrow(new RuntimeException());

        assertThrows(ResponseStatusException.class, () -> userService.logout(request));
    }
}
