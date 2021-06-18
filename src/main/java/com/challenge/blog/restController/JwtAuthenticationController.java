package com.challenge.blog.restController;

import java.util.Objects;

import com.challenge.blog.Model.ClientModel;
import com.challenge.blog.config.JwtTokenUtil;
import com.challenge.blog.entity.Client;
import com.challenge.blog.entity.JwtRequest;
import com.challenge.blog.entity.JwtResponse;
import com.challenge.blog.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
@CrossOrigin
@RequestMapping("/auth")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest, HttpSession session) throws Exception {

        authenticate(authenticationRequest.getMail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getMail());

        final String token = jwtTokenUtil.generateToken(userDetails);
        session.setAttribute("token", token);
        jwtTokenUtil.decodeToken(token);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/sign_up", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody ClientModel client) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(client));
    }


    private void authenticate(String mail, String password) throws Exception {
        try {
            System.out.println(mail  + " " + password);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(mail, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
