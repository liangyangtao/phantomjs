package weibo4j.examples.timeline;

import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

public class ShowStatus {

	public static void main(String[] args) {
//		AccessToken [accessToken=2.00Z8blhC0s9gJGfd382ffefcMYr5kB, expireIn=157679999, refreshToken=,uid=2478993465]
		String access_token = "2.00Z8blhC0s9gJGfd382ffefcMYr5kB";
		String id = "3675658334953252";
//		2478993465
		Timeline tm = new Timeline();
		tm.client.setToken(access_token);
		try {
			Status status = tm.showStatus(id);

				Log.logInfo(status.toString());

		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
