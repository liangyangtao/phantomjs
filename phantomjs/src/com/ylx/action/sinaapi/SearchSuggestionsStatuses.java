package com.ylx.action.sinaapi;

import com.ylx.util.FinalWord;

import weibo4j.Search;
import weibo4j.model.WeiboException;

/**
 * @author 梁杨桃
 * 
 *         根据关键词搜索微博
 * 
 * 
 *         由于新浪限制， 根据关键词搜索是微博不可用
 * 
 *         但可以搜索某一个话题下的微博， 不过得有高级权限才行
 * */
public class SearchSuggestionsStatuses {

	public static void searchSuggestionsStatuses(String keyword,
			String access_token) {
		Search search = new Search();
		search.client.setToken(FinalWord.MY_ACCESS_TOKEN);
		try {
			search.searchSuggestionsStatuses(keyword);
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		searchSuggestionsStatuses("凶案", null);
	}

}
