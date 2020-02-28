package com.trw.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author linhai123
 * @since 2018-06-25
 */
@TableName("t_news")
public class TNews extends Model<TNews> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "newsId", type = IdType.AUTO)
    private Integer newsId;
    private String title;
    private String content;
    private String publishDate;
    private String author;
    private Integer typeId;
    private Integer click;
    private Integer isHead;
    private Integer isImage;
    private String imageName;
    private Integer isHot;
    private String typeName;
    /**
     * 新闻热门标签
     */
    private String newsNote;


    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public Integer getIsHead() {
        return isHead;
    }

    public void setIsHead(Integer isHead) {
        this.isHead = isHead;
    }

    public Integer getIsImage() {
        return isImage;
    }

    public void setIsImage(Integer isImage) {
        this.isImage = isImage;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getNewsNote() {
        return newsNote;
    }

    public void setNewsNote(String newsNote) {
        this.newsNote = newsNote;
    }

    @Override
    protected Serializable pkVal() {
        return this.newsId;
    }

    @Override
    public String toString() {
        return "TNews{" +
        "newsId=" + newsId +
        ", title=" + title +
        ", content=" + content +
        ", publishDate=" + publishDate +
        ", author=" + author +
        ", typeId=" + typeId +
        ", click=" + click +
        ", isHead=" + isHead +
        ", isImage=" + isImage +
        ", imageName=" + imageName +
        ", isHot=" + isHot +
        ", typeName=" + typeName +
        ", newsNote=" + newsNote +
        "}";
    }
}
