package com.timerit.device;

/**
 * Created by trpgm on 2015-11-28.
 */
public class DeviceNotFoundException extends RuntimeException{
    Long id;
    String licencekey;
    public DeviceNotFoundException(Long id) {
        this.id = id;
    }
    public DeviceNotFoundException(String licencekey) {
        this.licencekey = licencekey;
    }
    public Long getId() {
        return id;
    }
    public String getLicencekey() { return licencekey;}
}
