package com.timerit.app;

import java.util.Date;

/**
 * Created by trpgm on 2015-11-28.
 */
public class LicenceExpiredException extends RuntimeException{
    Date expired;
    String licencekey;
    public LicenceExpiredException(Date expired) {
        this.expired = expired;
    }
    public LicenceExpiredException(String licencekey) {
        this.licencekey = licencekey;
    }
    public LicenceExpiredException(Date expired,String licencekey) {
        this.expired = expired;
        this.licencekey = licencekey;
    }
    public Date getExpired() {
        return expired;
    }
    public String getLicencekey() { return licencekey;}
}
