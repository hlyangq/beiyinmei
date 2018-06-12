package com.ningpai.deposit.mapper;

import com.ningpai.deposit.bean.Withdraw;
import com.ningpai.deposit.bean.WithDrawExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WithDrawMapper {
    int countByExample(WithDrawExample example);

    int deleteByExample(WithDrawExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Withdraw record);

    int insertSelective(Withdraw record);

    List<Withdraw> selectByExample(WithDrawExample example);

    Withdraw selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Withdraw record, @Param("example") WithDrawExample example);

    int updateByExample(@Param("record") Withdraw record, @Param("example") WithDrawExample example);

    int updateByPrimaryKeySelective(Withdraw record);

    int updateByPrimaryKey(Withdraw record);
}