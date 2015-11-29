package com.timerit.authtoken;

/**
 * Created by trpgm on 2015-11-29.
 */
public class AuthTokenNotFoundExcetion extends RuntimeException{
    private String tokenvalue;
    public AuthTokenNotFoundExcetion(String tokenvalue) {
        this.tokenvalue = tokenvalue;
    }
    public String getTokenvalue() {return tokenvalue;}
}
