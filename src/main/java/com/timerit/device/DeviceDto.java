package com.timerit.device;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * Created by trpgm on 2015-11-28.
 */
public class DeviceDto {
    @Data
    public static class Create {
        @NotBlank
        private Date expired;
        @NotBlank
        private Long ownerid;
    }
    @Data
    public static class Response {
        private Long id;
        private String licencekey;
        private Date expired;
        private Date registed;
        private Date updated;
    }

    @Data
    public static class Update {
        @NotBlank
        private Date expired;
    }
}
