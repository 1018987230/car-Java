package com.example.nft.service.impl;

import com.example.nft.commons.ServiceResultEnum;
import com.example.nft.dao.NoticeMapper;
import com.example.nft.entity.Notice;
import com.example.nft.service.NoticeService;
import com.example.nft.service.ex.InsertException;
import com.example.nft.service.ex.SelectException;
import com.example.nft.service.ex.UpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author wangyixiong
 * @Date 2022/10/23 下午10:52
 * @PackageName:com.example.nft.service.impl
 * @ClassName: NoticeServiceImpl
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private NoticeMapper noticeMapper;

    /**
     * 创建通知消息，传递过来的必须是Notice对象
     * @param notice
     * @return
     */
    @Override
    public String add(Notice notice) {
        String noticeUuid = new Date().getTime() + (int)((Math.random()*9+1)*100000) + "";
        notice.setNoticeUuid(noticeUuid);
        if (noticeMapper.insertNotice(notice) == 0){
            throw new InsertException(ServiceResultEnum.DB_INSERT_ERROR.getResult());
        }
        logger.info(notice.getNoticeUuid() + " inserted successfully");
        return ServiceResultEnum.SUCCESS.getResult();
    }


    /**
     * 修改通知消息的状态，方便排序
     * @param noticeUuid
     * @param noticeStatus
     * @return
     */
    @Override
    public String changeStatus(String noticeUuid, Integer noticeStatus) {
        // 是否为空
        if (noticeMapper.selectByUuid(noticeUuid) == null){
            throw new SelectException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        if(noticeMapper.updateStatus(noticeUuid, noticeStatus) == 0){
            throw new UpdateException(ServiceResultEnum.DB_UPDATE_ERROR.getResult());
        }
        logger.info(noticeUuid +" noticeStatus was modified to "+ noticeStatus);
        return ServiceResultEnum.SUCCESS.getResult();
    }

    /**
     * 通过noticeUuid查询指定通知信息
     * @param noticeUuid
     * @return
     */
    @Override
    public Notice findByUuid(String noticeUuid) {
        Notice dbRes = noticeMapper.selectByUuid(noticeUuid);
        // 查询为null 抛异常
        if(dbRes == null){
            throw new SelectException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        return dbRes;
    }


    /**
     * 通过用户id查询用户发出的通知和接受的通知
     * @param
     * @return
     */
    @Override
    public List<Notice> findByConsumerUuid(String consumerUuid, Integer currentPage) {

        return noticeMapper.selectByConsumerUuid(consumerUuid, (currentPage-1)*20);
    }

    /**
     * 通过店铺id查询用户发出的通知和接受的通知
     * @param
     * @return
     */
    @Override
    public HashMap<String, Object> findByStoreUuid(String storeUuid, Integer currentPage) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        List<Notice> notices = noticeMapper.selectByStoreUuid(storeUuid, (currentPage - 1) * 20);
        Integer sum = noticeMapper.selectCount();

        map.put("info", notices);
        map.put("sum", sum);


        return map;
    }

    @Override
    public List<Notice> findConsumerChangeByStore(String storeUuid, String noticeTitle, Integer currentPage) {
        System.out.println(storeUuid +  noticeTitle +  currentPage );
        return noticeMapper.selectConsumerChangeByStore(storeUuid, noticeTitle, (currentPage-1)*20);
    }
}
