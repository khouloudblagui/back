package org.example.authentification.config;

import org.springframework.security.core.GrantedAuthority;

public class SimpleGrantedAuthorityImpl implements GrantedAuthority {

    private String authority;

    // Constructeur par défaut requis pour la désérialisation
    public SimpleGrantedAuthorityImpl() {
    }

    // Constructeur avec paramètre
    public SimpleGrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
