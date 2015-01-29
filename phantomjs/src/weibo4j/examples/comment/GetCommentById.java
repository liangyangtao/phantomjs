package weibo4j.examples.comment;

import weibo4j.Comments;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Comment;
import weibo4j.model.CommentWapper;
import weibo4j.model.WeiboException;

public class GetCommentById {

	public static void main(String[] args) {
		String access_token = "2.00E9s_qBXAucED10e212a7170ik5hU";
		String id = "5602528222614298276";
		Comments cm =new Comments();
		cm.client.setToken(access_token);
		try {
			CommentWapper comment = cm.getCommentById(id);
			for(Comment c : comment.getComments()){
				Log.logInfo(c.toString());
			}
			System.out.println(comment.getTotalNumber());
			System.out.println(comment.getNextCursor());
			System.out.println(comment.getNextCursor());
			System.out.println(comment.getHasvisible());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
