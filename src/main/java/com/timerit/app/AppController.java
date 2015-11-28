package com.timerit.app;

import com.timerit.accounts.AccountNotFoundException;
import com.timerit.accounts.UserDuplicatedException;
import com.timerit.commons.ErrorResponse;
import com.timerit.device.Device;
import com.timerit.device.DeviceNotFoundException;
import com.timerit.keyword.KeywordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.java.SimpleFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.text.SimpleDateFormat;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by trpgm on 2015-11-28.
 */

@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private AppService service;

    @RequestMapping(value = "/open",method = POST)
    public ResponseEntity openApp(@RequestBody @Valid AppDto.Open dto, BindingResult result ) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.open(dto),HttpStatus.OK);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAccountNotFoundException(AccountNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getUsername() + "] 해당 사용자가 없습니다.");
        errorResponse.setCode("duplicated.username.exception");
        return errorResponse;
    }
    @ExceptionHandler(DeviceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDeviceNotFoundException(DeviceNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getLicencekey() + "] 존재하지 않는 라이센스키 입니다.");
        errorResponse.setCode("duplicated.username.exception");
        return errorResponse;
    }
    @ExceptionHandler(LicenceExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleLicenceExpiredException(LicenceExpiredException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");
        errorResponse.setMessage("유효기간["+sdf.format(e.getExpired())+"]라이센스키[" + e.getLicencekey() + "] 만료된 장비입니다.");
        errorResponse.setCode("expired.license.exception");
        return errorResponse;
    }

    @ExceptionHandler(KeywordNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleKeywordNotFoundException(KeywordNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("지정된 키워드가 없습니다.");
        errorResponse.setCode("no.keyword.exception");
        return errorResponse;
    }
}
