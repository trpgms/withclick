package com.timerit.accounts;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author trpgms
 */
public class AccountDto {

    @Data
    public static class Create {
        @NotBlank
        @Size(min = 5)
        private String username;

        @NotBlank
        @Size(min = 4)
        private String password;

        private String email;
        private String description;
    }

    @Data
    public static class Response {
        private Long id;
        private String username;
        private String email;
        private String description;
        private String status;
        private boolean admin;
        private Date joined;
        private Date updated;
    }

    @Data
    public static class Update {
        private String password;
        private String description;
        private String status;
        private String email;
        private boolean admin;
    }
}
