package com.trw.util;

public class PageUtil {
	
	public static int pageSize = 10;
	public static int backPageSize = 10;

	public static String getUpAndDownPagation(int totalNum,int currentPage,int pageSize,String typeId){
		int totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode=new StringBuffer();
		pageCode.append("<ul class='pager'>");
		if(currentPage==1){
			pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
		}else{
			pageCode.append("<li><a href='news/list?typeId="+typeId+"&page="+(currentPage-1)+"'>上一页</a></li>");			
		}
		pageCode.append("&nbsp;&nbsp;");
		if(currentPage==totalPage){
			pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
		}else{
			pageCode.append("<li><a href='news/list?typeId="+typeId+"&page="+(currentPage+1)+"'>下一页</a></li>");			
		}
		pageCode.append("</ul>");
		return pageCode.toString();
	}
	
	public static String getPagation(String targetUrl,int totalNum,int currentPage,int pageSize){
		int totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode=new StringBuffer();
		pageCode.append("<li><a href='"+targetUrl+"?page=1'>首页</a></li>");
		if(currentPage==1){
			pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
		}else{
			pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage-1)+"'>上一页</a></li>");
		}
		
		for(int i=currentPage-2;i<=currentPage+2;i++){
			if(i<1 || i>totalPage){
				continue;
			}
			if(i==currentPage){
				pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
			}else{
				pageCode.append("<li><a href='"+targetUrl+"?page="+i+"'>"+i+"</a></li>");
			}
			
		}
		
		if(currentPage==totalPage){
			pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
		}else{
			pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage+1)+"'>下一页</a></li>");
		}
		pageCode.append("<li><a href='"+targetUrl+"?page="+totalPage+"'>尾页</a></li>");
		return pageCode.toString();
	}
	
	public static String genNewsManageNavigation(String modName,String actionName){
		StringBuffer navCode=new StringBuffer();
		navCode.append("当前位置：&nbsp;&nbsp;");
		navCode.append("主页&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append(modName+"&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append(actionName+"&nbsp;&nbsp;");
		return navCode.toString();
	}
	
	public static String genNewsListNavigation(String typeName,String typeId){
		StringBuffer navCode=new StringBuffer();
		navCode.append("当前位置：&nbsp;&nbsp;");
		navCode.append("<a href='/news/goIndex'>主页</a>&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append("<a href='/news/news/list?typeId="+typeId+"'>"+typeName+"</a>");
		return navCode.toString();
	}
	
	public static String genNewsNavigation(String typeName,String typeId,String newsName){
		StringBuffer navCode=new StringBuffer();
		navCode.append("当前位置：&nbsp;&nbsp;");
		navCode.append("<a href='/news/goIndex'>主页</a>&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append("<a href='/news/news/list?typeId="+typeId+"'>"+typeName+"</a>&nbsp;&nbsp;>&nbsp;&nbsp;"+newsName);
		return navCode.toString();
	}
}
