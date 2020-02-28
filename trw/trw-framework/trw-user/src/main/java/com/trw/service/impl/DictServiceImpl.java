package com.trw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.DictMapper;
import com.trw.model.TrwTDict;
import com.trw.service.IDictService;
import com.trw.vo.CodeVo;
/**
* @ClassName: DictServiceImpl 
* @Description: 数据字典服务层
* @author luojing
* @date 2018年7月4日 上午10:17:59 
*
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, TrwTDict> implements IDictService {

    @Resource
    private DictMapper dictMapper;


    @Override
    public void delteDict(Integer dictId) {
        //删除这个字典的子词典
        Wrapper<TrwTDict> dictEntityWrapper = new EntityWrapper<>();
        dictEntityWrapper = dictEntityWrapper.eq("pid", dictId);
        dictMapper.delete(dictEntityWrapper);

        //删除这个词典
        dictMapper.deleteById(dictId);
    }

    @Override
    public List<TrwTDict> selectByCode(String code) {
        return this.baseMapper.selectByCode(code);
    }

    @Override
    public List<Map<String, Object>> list(String conditiion) {
        return this.baseMapper.list(conditiion);
    }

	@Override
	public List<CodeVo> selectCode() {
		List<CodeVo> codeList =baseMapper.selectCode(null);
        return codeList;
	}

	@Override
	public List<CodeVo> selectCode(String[] type) {
		List<CodeVo> codeList =baseMapper.selectCode(type);
        return codeList;
	}

}
