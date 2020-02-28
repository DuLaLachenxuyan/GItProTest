package com.trw.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.model.TrwTAccDetail;
import com.trw.model.TrwTAccount;
import com.trw.vo.AccountVo;

/**
* @ClassName: AccountMapper 
* @Description: 资金账户Mapper
* @author luojing
* @date 2018年7月4日 下午1:23:21 
*
 */
public interface AccountMapper extends BaseMapper<TrwTAccount> {

	List<AccountVo> selectAccDetail(Page<TrwTAccDetail> page, Map<String, String> param);


}
