package com.project.accomatch.JWT;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Map;

public class CustomUserDetails extends User {

    private final Map<String, String> additionalData;

    public CustomUserDetails(String username, String password, Map<String, String> additionalData, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.additionalData = additionalData;
    }



    public Map<String, String> getAdditionalData() {
        return additionalData;
    }
}
