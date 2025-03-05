package com.acl.pvoadministration.playload;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author jean paul
 */
@Getter
@Setter
public class LoginToken {

    @Column(name = "ID")
    private String id;

    @Column(name="USERNAME")
    private String username;

    @Column(name="PASSWORD")
    private String password;
}
