package com.acl.ticketingauth.security.impl;


/**
 * @author Zansouyé
 */


import com.acl.ticketingauth.repositories.UserRepository;
import com.acl.ticketingauth.security.UserServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserServices {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username {}", username);
        return userRepository.findByUsername(username)
                .map(UserPrincipal::create)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
