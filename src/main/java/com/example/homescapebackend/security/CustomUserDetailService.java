package com.example.homescapebackend.security;


import com.example.homescapebackend.entity.Customer;
import com.example.homescapebackend.entity.Role;
import com.example.homescapebackend.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class CustomUserDetailService implements UserDetailsService {
    private final CustomerRepo customerRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer user = customerRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
        return new User(user.getUsername(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
    }
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}