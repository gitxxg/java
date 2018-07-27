package cn.ghl.web.service.impl;

import cn.ghl.web.entity.MainTarget;
import cn.ghl.web.repository.MainTargetRepository;
import cn.ghl.web.service.MainTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 6/12/2018
 */
@Service
public class MainTargetServiceImpl implements MainTargetService {

    @Autowired
    private MainTargetRepository mainTargetRepository;


    @Override
    public MainTarget save(MainTarget mainTarget) {
        return mainTargetRepository.save(mainTarget);
    }

    @Override
    public Iterable<MainTarget> findAll() {
        return mainTargetRepository.findAll();
    }
}
