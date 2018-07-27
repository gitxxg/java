package cn.ghl.web.repository;


import cn.ghl.web.entity.MainTarget;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 6/11/2018
 */
public interface MainTargetRepository extends ElasticsearchRepository<MainTarget, String> {

}

