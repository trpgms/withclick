package com.timerit.device;

/**
 * Created by trpgm on 2015-11-28.
 */
public class DeviceNotFoundException extends RuntimeException{
    Long id;

    public DeviceNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
