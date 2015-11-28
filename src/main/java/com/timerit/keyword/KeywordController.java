package com.timerit.keyword;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by trpgm on 2015-11-28.
 */
@RestController
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
}
