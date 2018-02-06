package com.banma.hermes.consumer.respository;

import com.banma.hermes.consumer.domain.SystemInfo;

import java.util.List;

public interface SystemInfoMapper {
    int deleteByPrimaryKey(Integer systemId);

    int insert(SystemInfo record);

    int insertSelective(SystemInfo record);

    SystemInfo selectByPrimaryKey(Integer systemId);

    int updateByPrimaryKeySelective(SystemInfo record);

    int updateByPrimaryKey(SystemInfo record);

    List<Integer> selectIdList();
}