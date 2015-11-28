package com.timerit.keyword;

import com.timerit.device.Device;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;


/**
 * Created by trpgm on 2015-11-28.
 */
public class KeywordDto {
    @Data
    public static class Create {
        @NotBlank
        private String searchword;
        @NotBlank
        private String url;
        @NotNull
        private Integer waitopen;
        @NotNull
        private Integer waitsearch;

        private String description;
        @NotNull
        private Long deviceid;
    }
    @Data
    public static class Response {
        private Long id;

        private String searchword;

        private String url;

        private Integer priority;

        private Integer waitopen;

        private Integer waitsearch;

        private String description;

        private Device device;
    }

    @Data
    public static class Update {
        @NotBlank
        private String searchword;
        @NotBlank
        private String url;
        @NotBlank
        private Integer priority;
        @NotBlank
        private Integer waitopen;
        @NotBlank
        private Integer waitsearch;

        private String description;
    }
}
