package com.timerit.device;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by trpgm on 2015-11-28.
 */
public class DeviceDto {
    @Data
    public static class Create {
        @NotNull
        private Date expired;
        @NotNull
        private Long ownerid;
    }
    @Data
    public static class Response {
        private Long id;
        private String licencekey;
        private Long ownerid;
        private Date expired;
        private Date registed;
        private Date updated;
    }

    @Data
    public static class Update {
        @NotNull
        private Date expired;
    }
}
