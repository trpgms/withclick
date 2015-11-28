package com.timerit.app;

import lombok.Data;
import org.hibernate.internal.util.type.PrimitiveWrapperHelper;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by trpgm on 2015-11-28.
 */
public class AppDto {
    @Data
    public static class Open {
        @NotBlank
        @Size(min = 5)
        protected String username;

        @NotBlank
        @Size(min = 4)
        private String licencekey;

        // 검색결과
        private Long keywordid;
        private String searchword;
        private String url;
        private Integer rank;
        private Date searched;
        private Integer battery;
        private String comments;
    }

    @Data
    public static class Result {
        // account
        private String username;
        private String email;
        private String status;
        // device
        private String licencekey;
        private Date expired;
        // keyword
        private Long keywordid;
        private String searchword;
        private String url;
        private Integer waitopen;
        private Integer waitsearch;
        private String description;

    }
}
