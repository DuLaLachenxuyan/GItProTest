package com.trw.factory;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.plugins.Page;
import com.trw.enums.Order;
import com.trw.util.HttpKit;
import com.trw.util.StrKit;
import com.trw.util.ToolUtil;


public class PageFactory<T> {

    public Page<T> defaultPage() {
        HttpServletRequest request = HttpKit.getRequest();
        String limitStr = request.getParameter("limit");
        if(StrKit.isBlank(limitStr)) {
        	limitStr="10";
        }
        String pageNumStr = request.getParameter("pageNum");
        if(StrKit.isBlank(pageNumStr)) {
        	pageNumStr="1";
        }
        int limit = Integer.valueOf(limitStr);     //每页多少条数据
        int pageNum = Integer.valueOf(pageNumStr);   //第几页
        String sort = request.getParameter("sort");         //排序字段名称
        String order = request.getParameter("order");       //asc或desc(升序或降序)
        if (ToolUtil.isEmpty(sort)) {
            Page<T> page = new Page<>(pageNum, limit);
            page.setOpenSort(false);
            return page;
        } else {
            Page<T> page = new Page<>(pageNum, limit, sort);
            if (Order.ASC.getDes().equals(order)) {
                page.setAsc(true);
            } else {
                page.setAsc(false);
            }
            return page;
        }
    }
}