package com.timerit.accounts;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AccountTest {

    @Test
    public void getterSetter() {
        Account account = new Account();
        account.setUsername("trpgms");
        account.setPassword("password");
        assertThat(account.getUsername(), is("trpgms"));
    }

}