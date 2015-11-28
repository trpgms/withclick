package com.timerit.keyword;

import com.timerit.device.Device;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;


/**
 * Created by trpgm on 2015-11-28.
 */
public class KeywordDto {
    @Data
    public static class Create {
        @NotBlank
        private String keyword;
        @NotBlank
        private String url;

        private Integer termsec;

        private Integer priority;

        private String description;
        @NotBlank
        private Long deviceid;
    }
    @Data
    public static class Response {
        private Long id;

        private String keyword;

        private String url;

        private Integer priority;

        private Integer termsec;

        private String description;

        private Device device;
    }

    @Data
    public static class Update {
        @NotBlank
        private String keyword;
        @NotBlank
        private String url;
        @NotBlank
        private Integer priority;
        @NotBlank
        private Integer termsec;

        private String description;
    }
}
