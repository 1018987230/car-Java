package com.example.nft.dao;

import com.example.nft.entity.ChargeActivityRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 充值活动参与表 Mapper 接口
 * </p>
 *
 * @author xxx
 * @since 2024-03-20
 */
@Mapper
public interface ChargeActivityRecordMapper extends BaseMapper<ChargeActivityRecord> {

}
