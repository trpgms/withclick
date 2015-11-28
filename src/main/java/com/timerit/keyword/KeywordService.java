package com.timerit.keyword;

import com.timerit.device.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        keyword.setDevice(deviceRepository.findOne(dto.getDeviceid()));

        return repository.save(keyword);
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
        keyword.setKeyword(dto.getKeyword());
        keyword.setUrl(dto.getUrl());
        keyword.setPriority(dto.getPriority());
        keyword.setTermsec(dto.getTermsec());
        keyword.setDescription(dto.getDescription());

        return repository.save(keyword);
    }

    public void deleteKeyword(Long id){
        repository.delete(getKeyword(id));
    }
}
