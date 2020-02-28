package com.trw.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.service.IService;
import com.trw.model.TrwTDict;
import com.trw.vo.CodeVo;

/**
* @ClassName: IDictService 
* @Description: 数据字典服务层
* @author luojing
* @date 2018年7月4日 上午10:17:25 
*
 */
public interface IDictService extends IService<TrwTDict> {


    /**
     * 删除字典
     */
    void delteDict(Integer dictId);

    /**
     * 根据编码获取词典列表
     */
    List<TrwTDict> selectByCode(@Param("code") String code);

    /**
     * 查询字典列表
     */
    List<Map<String, Object>> list(@Param("condition") String conditiion);
    /**
     * 查询字典列表
     */
	List<CodeVo> selectCode();
	/**
	 * 查询字典列表
	 */
	List<CodeVo> selectCode(String[] type);
    

}
