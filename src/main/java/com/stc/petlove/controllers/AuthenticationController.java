package com.stc.petlove.controllers;

import com.stc.petlove.dtos.LoginDto;
import com.stc.petlove.dtos.TokenDetails;
import com.stc.petlove.dtos.RegisterDto;
import com.stc.petlove.entities.TaiKhoan;
import com.stc.petlove.exceptions.InvalidException;
import com.stc.petlove.exceptions.UserNotFoundAuthenticationException;
import com.stc.petlove.securities.CustomUserDetailsService;
import com.stc.petlove.securities.JwtTokenUtils;
import com.stc.petlove.securities.JwtUserDetails;
import com.stc.petlove.securities.UserAuthenticationToken;
import com.stc.petlove.services.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/15/23
 * Time      : 9:38 AM
 * Filename  : AuthenticationController
 */
@Slf4j
@RestController
@RequestMapping("/rest")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtTokenUtils jwtTokenUtils;

    private final UserService userService;

//    public AuthenticationController(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService,
//                                    JwtTokenUtils jwtTokenUtils) {
//        this.authenticationManager = authenticationManager;
//        this.customUserDetailsService = customUserDetailsService;
//        this.jwtTokenUtils = jwtTokenUtils;
//    }

    @PostMapping("/signup")
    public ResponseEntity<TaiKhoan> signup(@Valid @RequestBody RegisterDto registerDto){
        return new ResponseEntity<>(userService.signup(registerDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "login form (username, password), avatar null")
    @PostMapping("/login")
    public ResponseEntity<TokenDetails> login(@Valid @RequestBody LoginDto dto) {
        UserAuthenticationToken authenticationToken = new UserAuthenticationToken(
                dto.getUsername(),
                dto.getPassword(),
                true
        );
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (UserNotFoundAuthenticationException | BadCredentialsException ex) {
            throw new InvalidException(ex.getMessage());
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        final JwtUserDetails userDetails = customUserDetailsService
                .loadUserByUsername(dto.getUsername());
        final TokenDetails result = jwtTokenUtils.getTokenDetails(userDetails, null);
        log.info(String.format("User %s login at %s", dto.getUsername(), new Date()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/hello")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> sayHello(Principal principal) {
        return new ResponseEntity<>(String.format("Hello %s", principal.getName()), HttpStatus.OK);
    }


}
