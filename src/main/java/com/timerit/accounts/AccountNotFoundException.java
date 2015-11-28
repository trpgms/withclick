package com.timerit.accounts;

/**
 * @author trpgms
 */
public class AccountNotFoundException extends RuntimeException {

    Long id;

    public AccountNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
