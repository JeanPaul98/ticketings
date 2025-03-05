package com.acl.ticketingauth.playload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author jean paul
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginToken {

    private String id;

    private String username;

    private String password;
}
