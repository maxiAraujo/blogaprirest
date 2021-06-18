package com.challenge.blog.service;

import java.util.ArrayList;

import com.challenge.blog.Model.ClientModel;
import com.challenge.blog.entity.Client;
import com.challenge.blog.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public Client save(ClientModel userModel) {
        Client newUser = new Client();
        newUser.setMail(userModel.getMail());
        newUser.setPassword(bcryptEncoder.encode(userModel.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client user = userRepository.findClientByMail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No existe ningun usuario registrado a este email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getMail(), user.getPassword(),
                new ArrayList<>());
    }

    public Client findByMail(String email){
        return userRepository.findClientByMail(email);
    }
}
