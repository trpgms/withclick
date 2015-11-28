package com.timerit.accounts;

/**
 * @author trpgms
 */
public class AccountNotFoundException extends RuntimeException {

    Long id;
    String username;
    public AccountNotFoundException(Long id) {
        this.id = id;
    }
    public AccountNotFoundException(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
}
