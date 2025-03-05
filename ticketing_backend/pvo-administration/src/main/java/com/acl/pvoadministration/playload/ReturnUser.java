package com.acl.pvoadministration.playload;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnUser {
    @Column(name="USERNAME")
    private String username;

    @Column(name="PASSWORD")
    private String password;
}
