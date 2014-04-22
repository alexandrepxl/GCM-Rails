package sbu.mad.android2rails;

public interface Config {

	// used to share GCM regId with application server - using php app server
	static final String APP_SERVER_URL = "http://192.168.0.12:3000/api/v1/devices/test";

	// GCM server using java
	// static final String APP_SERVER_URL =
	// "http://192.168.1.17:8080/GCM-App-Server/GCMNotification?shareRegId=1";

	// Google Project Number
	static final String GOOGLE_PROJECT_ID = "34797616824";
	static final String MESSAGE_KEY = "message";
	
}
