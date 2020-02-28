package com.trw.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.TrwTUserCardMapper;
import com.trw.model.TrwTUserCard;
import com.trw.service.UserCardService;

/**
 * <p>
 * 用户身份证信息 服务实现类
 * </p>
 *
 * @author gongzhen123
 * @since 2018-07-09
 */
@Service
public class UserCardServiceImpl extends ServiceImpl<TrwTUserCardMapper, TrwTUserCard> implements UserCardService {

}
