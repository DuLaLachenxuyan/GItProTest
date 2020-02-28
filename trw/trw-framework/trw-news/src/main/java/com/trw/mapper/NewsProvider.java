package com.trw.mapper;

import com.trw.model.News;
import com.trw.util.StrKit;
import com.trw.vo.PageBean;

public class NewsProvider {
	
	public String newsList(News s_news,PageBean pageBean,String s_bPublishDate,String s_aPublishDate) {
		StringBuffer sb=new StringBuffer("select t1.newsId,t1.title,t1.content,t1.publishDate,t1.author,t1.typeId,t1.click,t1.isHead,t1.isImage,t1.imageName,t1.isHot,t2.typeName from t_news t1 inner join t_newsType t2 on t1.typeId=t2.newsTypeId where 1=1 ");
		if(s_news.getTypeId()!=-1){
			sb.append(" and t1.typeId="+s_news.getTypeId());
		}
		if(StrKit.isNotEmpty(s_news.getTitle())){
			sb.append(" and t1.title like '%"+s_news.getTitle()+"%'");
		}
		if(StrKit.isNotEmpty(s_bPublishDate)){
			sb.append(" and TO_DAYS(t1.publishDate)>=TO_DAYS('"+s_bPublishDate+"')");
		}
		if(StrKit.isNotEmpty(s_aPublishDate)){
			sb.append(" and TO_DAYS(t1.publishDate)<=TO_DAYS('"+s_aPublishDate+"')");
		}
		sb.append(" order by t1.publishDate desc ");
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		return sb.toString();
	}
	
	public String newsCount(News s_news,String s_bPublishDate,String s_aPublishDate) {
		StringBuffer sb=new StringBuffer("select count(*) as total from t_news");
		if(s_news.getTypeId()!=-1){
			sb.append(" and typeId="+s_news.getTypeId());
		}
		if(StrKit.isNotEmpty(s_news.getTitle())){
			sb.append(" and title like '%"+s_news.getTitle()+"%'");
		}
		if(StrKit.isNotEmpty(s_bPublishDate)){
			sb.append(" and TO_DAYS(publishDate)>=TO_DAYS('"+s_bPublishDate+"')");
		}
		if(StrKit.isNotEmpty(s_aPublishDate)){
			sb.append(" and TO_DAYS(publishDate)<=TO_DAYS('"+s_aPublishDate+"')");
		}
		return sb.toString().replaceFirst("and", "where");
	}

}
