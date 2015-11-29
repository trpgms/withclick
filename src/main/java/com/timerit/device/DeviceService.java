package com.timerit.device;

import com.timerit.accounts.Account;
import com.timerit.accounts.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by trpgm on 2015-11-28.
 */

@Service
@Transactional
@Slf4j
public class DeviceService {
    @Autowired
    private DeviceRepository repository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Device createDevice(DeviceDto.Create dto) {
        Device device = modelMapper.map(dto,Device.class);

        device.setLicencekey(RandomStringUtils.randomAlphanumeric(16).toUpperCase());
        device.setOwner(accountRepository.findOne(dto.getOwnerid()));

        Date now = new Date();
        device.setRegisted(now);
        device.setUpdated(now);
        return repository.save(device);
    }

    public PageImpl<DeviceDto.Response> getDevices(Long ownerid, Pageable pageable) {
        Account account = accountRepository.findOne(ownerid);
        Page<Device> page = repository.findByOwnerOrderByExpiredDesc(account, pageable);
        List<DeviceDto.Response> content = page.getContent().parallelStream()
                .map(device -> modelMapper.map(device, DeviceDto.Response.class))
                .collect(Collectors.toList());
        for (DeviceDto.Response response : content) {
            response.setOwnerid(ownerid);
        }
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    public Device getDevice(Long id) {
        Device device = repository.findOne(id);
        if(device == null) {
            throw new DeviceNotFoundException(id);
        }
        return device;
    }
    public Device updateDevice(Long id, DeviceDto.Update dto) {
        Device device = getDevice(id);
        device.setExpired(dto.getExpired());
        return repository.save(device);
    }

    public void deleteDevice(Long id) {
        repository.delete(getDevice(id));
    }
}
