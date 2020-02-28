package com.trw.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.trw.model.TrwTAccDetail;
import com.trw.model.TrwTAccount;
import com.trw.vo.AccountVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-16
 */
public interface IAccountService extends IService<TrwTAccount> {

	List<AccountVo> selectAccDetail(Page<TrwTAccDetail> page, Map<String, String> param);




}
