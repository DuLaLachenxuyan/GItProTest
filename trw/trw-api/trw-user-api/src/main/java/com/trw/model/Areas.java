package com.trw.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 行政区域县区信息表
 * </p>
 *
 * @author 123
 * @since 2018-07-06
 */
@Getter
@Setter
public class Areas extends Model<Areas> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String areaid;
    private String area;
    private String cityid;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
