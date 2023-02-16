package com.example.nft.controller;

import com.example.nft.controller.param.NoticeParam;
import com.example.nft.controller.param.PageParam;
import com.example.nft.dao.NoticeMapper;
import com.example.nft.entity.Notice;
import com.example.nft.service.ConsumerService;
import com.example.nft.service.NoticeService;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author wangyixiong
 * @Date 2022/10/23 下午10:34
 * @PackageName:com.example.nft.controller
 * @ClassName: NoticeController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Resource
    private NoticeMapper noticeMapper;

    @Resource
    private NoticeService noticeService;

    @Resource
    private ConsumerService consumerService;

    /**
     * 仅做测试
     * @return
     */
    @PostMapping("/add")
    public Result add(){
        Notice notice = new Notice();
        String noticeUuid = new Date().getTime() + (int)((Math.random()*9+1)*100000) + "";
        notice.setNoticeUuid(noticeUuid);
        notice.setNoticeFrom("12345");
        notice.setNoticeTo("54231");
        notice.setNoticeTitle("Cool");
        notice.setNoticeContent("i am very cool! yse !");
        noticeMapper.insertNotice(notice);
        return null;
    }

    /**
     * 获取noticeUuid和noticeStatus,进行通知消息状态的修改
     * @param noticeParam
     * @return
     */
    @PostMapping("/change")
    public Result change(@RequestBody NoticeParam noticeParam){
        String result = noticeService.changeStatus(noticeParam.getNoticeUuid(), noticeParam.getNoticeStatus());

        return ResultGenerator.genSuccessResult(result);
    }


    /**
     *根据uuid查询
     * @param noticeParam
     * @return
     */
    @PostMapping("/findByNoticeUuId")
    public Result findByNoticeUuId(@RequestBody NoticeParam noticeParam){
        Notice result = noticeService.findByUuid(noticeParam.getNoticeUuid());

        return ResultGenerator.genSuccessResult(result);
    }

    /**
     * 根据顾客id查询
     * @param
     * @return
     */
    @PostMapping("/findByConsumerId")
    public Result findByConsumerId(@RequestBody PageParam pageParam){
        List<Notice> result = noticeService.findByConsumerUuid(pageParam.getConsumerUuid(), pageParam.getCurrentPage());
        return ResultGenerator.genSuccessResult(result);
    }

    /**
     * 根据店铺id查询
     * @param
     * @return
     */
    @PostMapping("/findByStoreId")
    public Result findByStoreId(@RequestBody PageParam pageParam){
        HashMap<String, Object> result = noticeService.findByStoreUuid(pageParam.getStoreUuid(), pageParam.getCurrentPage());
        return ResultGenerator.genSuccessResult(result);
    }

    /**
     * 店铺uuid和顾客uuid联合查询
     * @param pageParam
     * @return
     */
    @PostMapping("/findByStoreConsumer")
    public Result findByStoreConsumer(@RequestBody PageParam pageParam){
        HashMap<String, Object> result = noticeService.findByStoreConsumer(pageParam.getStoreUuid(), pageParam.getConsumerUuid(), pageParam.getCurrentPage());
        return ResultGenerator.genSuccessResult(result);
    }

    /**
     * 店铺uuid和顾客手机号联合查询
     * @param pageParam
     * @return
     */
    @PostMapping("/findByStoreConsumerPhone")
    public Result findByStoreConsumerPhone(@RequestBody PageParam pageParam){
        // 顾客phone转uuid
        String consumerUuid = consumerService.findByPhone(pageParam.getConsumerPhone()).getConsumerUuid();
        System.out.println(consumerUuid);
        HashMap<String, Object> result = noticeService.findByStoreConsumer(pageParam.getStoreUuid(), consumerUuid, pageParam.getCurrentPage());
        return ResultGenerator.genSuccessResult(result);
    }



    @PostMapping("/findConsumerChangeByStore")
    public Result findConsumerChangeByStore(@RequestBody PageParam pageParam){
        List<Notice> result = noticeService.findConsumerChangeByStore(pageParam.getStoreUuid(), pageParam.getNoticeTitle(), pageParam.getCurrentPage());

        return ResultGenerator.genSuccessResult(result);

    }
}




