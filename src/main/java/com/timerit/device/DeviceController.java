package com.timerit.device;

import com.timerit.accounts.Account;
import com.timerit.accounts.AccountDto;
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
@RequestMapping("/api")
public class DeviceController {
    @Autowired
    private DeviceService service;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/devices",method = POST)
    public ResponseEntity createDevice(@RequestBody @Valid DeviceDto.Create create, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Device device = service.createDevice(create);
        return new ResponseEntity<>(modelMapper.map(device, DeviceDto.Response.class),HttpStatus.CREATED);
    }

    @RequestMapping(value = "/devices/{ownerid}",method = GET)
    public PageImpl<DeviceDto.Response> getDevices(@PathVariable Long ownerid, Pageable pageable) {
        return service.getDevices(ownerid,pageable);
    }

    @RequestMapping(value = "/device/{id}",method = GET)
    public ResponseEntity getDevice(@PathVariable Long id) {
        Device device = service.getDevice(id);
        return new ResponseEntity<>(modelMapper.map(device,DeviceDto.Response.class),HttpStatus.OK);
    }

    @RequestMapping(value = "/device/{id}",method = PUT)
    public ResponseEntity updateDevice(@PathVariable Long id, @RequestBody @Valid DeviceDto.Update update, BindingResult result ) {
        if(result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Device updatedDevice = service.updateDevice(id,update);
        return new ResponseEntity<>(modelMapper.map(updatedDevice,DeviceDto.Response.class),HttpStatus.OK);
    }

    @RequestMapping(value = "/device/{id}",method = DELETE)
    public ResponseEntity deleteDevice(@PathVariable Long id) {
        service.deleteDevice(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
