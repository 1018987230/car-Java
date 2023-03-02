package com.example.nft.service.impl;

import com.example.nft.commons.ServiceResultEnum;
import com.example.nft.dao.ConsumeLogMapper;
import com.example.nft.entity.ConsumeLog;
import com.example.nft.service.ConsumeLogService;
import com.example.nft.service.ex.InsertException;
import com.example.nft.utils.ResultGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author wangyixiong
 * @Date 2023/2/21 下午5:39
 * @PackageName:com.example.nft.service.impl
 * @ClassName: ConsumeLog
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class ConsumeLogServiceImpl implements ConsumeLogService {

    @Resource
    private ConsumeLogMapper consumeLogMapper;

    @Override
    public String add(ConsumeLog consumeLog) {
        Integer dbRes =  consumeLogMapper.insert(consumeLog);
        if(dbRes != 1 ){
            throw new InsertException("insert DB failed." );
        }
        return ServiceResultEnum.SUCCESS.getResult();
    }

    @Override
    public HashMap<String, Object> findMany(String storeUuid, String consumerUuid, Integer currentPage) {

        List<ConsumeLog> info = consumeLogMapper.selectMany(storeUuid, consumerUuid, (currentPage-1)*20);
        Integer sum = consumeLogMapper.selectManyCount(storeUuid, consumerUuid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("info", info);
        map.put("sum", sum);
        return map;
    }
}
