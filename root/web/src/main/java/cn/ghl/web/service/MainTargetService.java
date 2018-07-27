package cn.ghl.web.service;

import cn.ghl.web.entity.MainTarget;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 6/12/2018
 */
public interface MainTargetService {

    MainTarget save(MainTarget mainTarget);

    Iterable<MainTarget> findAll();
}
