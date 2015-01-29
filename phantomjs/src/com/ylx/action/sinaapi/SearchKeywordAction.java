package com.ylx.action.sinaapi;

import com.ylx.util.FinalWord;

import weibo4j.Search;
import weibo4j.model.WeiboException;
 
/**
 * @author 梁杨桃
 *    
 * */
public class SearchKeywordAction {
	public static void main(String[] args) {
		Search search = new Search();
		search.client.setToken(FinalWord.MY_ACCESS_TOKEN);
		try {
//			联想搜索的数据范围是：v用户、粉丝500以上的达人、粉丝600以上的普通用户。
//			返回的记录条数，默认为10。
			search.searchSuggestionsUsers("miss" ,1);
			
			
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
//	 public void   
	 
	
}
