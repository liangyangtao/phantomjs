package com.ylx.entity;

import java.util.Date;

/**
 * 
 * @author 梁杨桃
 * */
public class Author {
	// 微博抓取实体类
	public int auto_id; // 微博自增编号
	public String author_id; // 用户编号
	public String author_nickName;// 用户昵称
	public String author_url; // 用户微博url
	public String author_img_url; // 用户的头像

	public String mid; // 微博 的Mid
	public String content; // 微博的内容
	public String content_image; // 微博的图片（可能没有）

	public Date news_time; // 微博发布的时间
	public String follow; // 微博发布的客户端

	public Date crawl_time; // 抓取的时间

	public int getAuto_id() {
		return auto_id;
	}

	public void setAuto_id(int auto_id) {
		this.auto_id = auto_id;
	}

	public String getAuthor_id() {
		return author_id;
	}

	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}

	public String getAuthor_nickName() {
		return author_nickName;
	}

	public void setAuthor_nickName(String author_nickName) {
		this.author_nickName = author_nickName;
	}

	public String getAuthor_url() {
		return author_url;
	}

	public void setAuthor_url(String author_url) {
		this.author_url = author_url;
	}

	public String getAuthor_img_url() {
		return author_img_url;
	}

	public void setAuthor_img_url(String author_img_url) {
		this.author_img_url = author_img_url;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getNews_time() {
		return news_time;
	}

	public void setNews_time(Date news_time) {
		this.news_time = news_time;
	}

	public String getFollow() {
		return follow;
	}

	public void setFollow(String follow) {
		this.follow = follow;
	}

	public Date getCrawl_time() {
		return crawl_time;
	}

	public void setCrawl_time(Date crawl_time) {
		this.crawl_time = crawl_time;
	}

	public String getContent_image() {
		return content_image;
	}

	public void setContent_image(String content_image) {
		this.content_image = content_image;
	}
}
