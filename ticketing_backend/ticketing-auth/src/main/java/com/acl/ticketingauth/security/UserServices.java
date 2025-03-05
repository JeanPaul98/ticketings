package com.acl.ticketingauth.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserServices extends UserDetailsService {
}
