package com.timerit.keyword;

import com.timerit.device.Device;
import com.timerit.device.DeviceNotFoundException;
import com.timerit.device.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
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
public class KeywordService {
    @Autowired
    private KeywordRepository repository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Keyword createKeyword(KeywordDto.Create dto){
        Keyword keyword = modelMapper.map(dto,Keyword.class);

        Device device = deviceRepository.findOne(dto.getDeviceid());
        if(device == null) {
            throw new DeviceNotFoundException(dto.getDeviceid());
        }
        // 우선 순위 마지막을 붙여준다.
        Keyword lastkeyword = repository.findTopByDeviceOrderByPriorityDesc(device);
        if(lastkeyword == null) {
            keyword.setPriority(1);
        } else {
            keyword.setPriority(lastkeyword.getPriority() + 1);
        }
        keyword.setDevice(device);
        keyword.setRegisted(new Date());

        return repository.save(keyword);
    }

    public PageImpl<KeywordDto.Response> getKeywords(Long deviceid, Pageable pageable) {
        Device device = deviceRepository.findOne(deviceid);
        Page<Keyword> page = repository.findByDeviceOrderByPriorityAsc(device, pageable);
        List<KeywordDto.Response> content = page.getContent().parallelStream()
                .map(keyword -> modelMapper.map(keyword, KeywordDto.Response.class))
                .collect(Collectors.toList());
        for (KeywordDto.Response response : content) {
            response.setDeviceid(deviceid);
        }
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    public Keyword getKeyword(Long id){
        Keyword keyword = repository.findOne(id);
        if(keyword == null) {
            throw new KeywordNotFoundException(id);
        }
        return keyword;
    }

    public Keyword updateKeyword(Long id, KeywordDto.Update dto){
        Keyword keyword = getKeyword(id);
        keyword.setSearchword(dto.getSearchword());
        keyword.setUrl(dto.getUrl());
        keyword.setPriority(dto.getPriority());
        keyword.setWaitopen(dto.getWaitopen());
        keyword.setWaitsearch(dto.getWaitsearch());
        keyword.setDescription(dto.getDescription());

        return repository.save(keyword);
    }

    public void deleteKeyword(Long id){
        repository.delete(getKeyword(id));
    }
}
