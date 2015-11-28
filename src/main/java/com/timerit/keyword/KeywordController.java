package com.timerit.keyword;

import com.timerit.commons.ErrorResponse;
import com.timerit.device.DeviceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by trpgm on 2015-11-28.
 */
@RestController
@RequestMapping("/api")
public class KeywordController {
    @Autowired
    private KeywordService service;

    @Autowired
    private KeywordRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/keywords",method = POST)
    public ResponseEntity createKeyword(@RequestBody @Valid KeywordDto.Create create, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Keyword keyword = service.createKeyword(create);
        return new ResponseEntity<>(modelMapper.map(keyword, KeywordDto.Response.class),HttpStatus.CREATED);
    }

    @RequestMapping(value = "/keywords",method = GET)
    public PageImpl<KeywordDto.Response> getKeywords(Pageable pageable) {
        Page<Keyword> page = repository.findAll(pageable);
        List<KeywordDto.Response> content = page.getContent().parallelStream()
                .map(device -> modelMapper.map(device, KeywordDto.Response.class))
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    @RequestMapping(value = "/keyword/{id}",method = GET)
    public ResponseEntity getKeyword(@PathVariable Long id) {
        Keyword keyword = service.getKeyword(id);
        return new ResponseEntity<>(modelMapper.map(keyword,KeywordDto.Response.class),HttpStatus.OK);
    }
    @RequestMapping(value = "/keyword/{id}",method = PUT)
    public ResponseEntity updateKeyword(@PathVariable Long id, @RequestBody @Valid KeywordDto.Update update, BindingResult result ) {
        if(result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Keyword obj = service.updateKeyword(id,update);
        return new ResponseEntity<>(modelMapper.map(obj,KeywordDto.Response.class),HttpStatus.OK);
    }

    @RequestMapping(value = "/keyword/{id}",method = DELETE)
    public ResponseEntity deleteKeyword(@PathVariable Long id) {
        service.deleteKeyword(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(DeviceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDeviceNotFoundException(DeviceNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getId() + "] 존재하지 않는 디바이스입니다.");
        errorResponse.setCode("device.id.exception");
        return errorResponse;
    }
}
