package net.behoo.appmarket.http;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import net.behoo.appmarket.data.AppInfo;

public class AppDetailParser {
	static public AppInfo parse(InputStream stream) {
		if(null == stream) {
			throw new IllegalArgumentException();
		}
		
		AppInfo appInfo = new AppInfo();
		XmlPullParser parser = Xml.newPullParser();
    	try {
    		parser.setInput(stream, null);
	        int eventType = parser.getEventType();
	        boolean done = false;
	        
	        String tagName = null;
	        boolean bDataValid = true;
	        while (eventType != XmlPullParser.END_DOCUMENT && !done) {
	        	switch (eventType) {
                case XmlPullParser.START_TAG:
                	tagName = parser.getName();
                	if( tagName.equalsIgnoreCase("BH_S_App_Detail")) {
                		bDataValid = true;
                	}
                	else if (bDataValid) {
                		if (tagName.equalsIgnoreCase("BH_D_App_Name")) {
                			appInfo.mAppName = parser.nextText();
	                	}
	                	else if(tagName.equalsIgnoreCase("BH_D_App_Version")) {
	                		appInfo.mAppVersion = parser.nextText();
	                	}
	                	else if(tagName.equalsIgnoreCase("BH_D_App_Author")) {
	                		appInfo.mAppAuthor = parser.nextText();
	                	}
	                	else if(tagName.equalsIgnoreCase("BH_D_App_Pic_Url")) {
	                		appInfo.mAppImageUrl = parser.nextText();
	                	}
	                	else if(tagName.equalsIgnoreCase("BH_D_App_Short_Desc")) {
	                		appInfo.mAppShortDesc = parser.nextText();
	                	}
	                	else if(tagName.equalsIgnoreCase("BH_D_App_Code")) {
	                		appInfo.mAppCode = parser.nextText();
	                	}
	                	else if(tagName.equalsIgnoreCase("BH_D_App_Long_Desc")) {
	                		appInfo.mAppDesc = parser.nextText();
	                	}
						else if(tagName.equalsIgnoreCase("BH_I_App_Size_KB")) {
							appInfo.mAppSize = parser.nextText();                		
						}
						else if(tagName.equalsIgnoreCase("BH_I_App_Remoter_Score")) {
							appInfo.mAppRemoteCntlScore = parser.nextText();
						}
						else if(tagName.equalsIgnoreCase("BH_D_App_Screenshots")) {
							appInfo.mAppScreenShorts = parser.nextText();
						}
						else if(tagName.equalsIgnoreCase("BH_D_App_Review")) {
							appInfo.mAppReview = parser.nextText();
						}
						else if(tagName.equalsIgnoreCase("BH_D_App_Change_Log")) {
							appInfo.mAppChangelog = parser.nextText();
						}
						else {
							done = true;
						}
                	}
                	break;
                case XmlPullParser.END_TAG:
                	tagName = parser.getName();
                	if (tagName.equalsIgnoreCase("BH_S_App_Detail") && bDataValid) {
                		appInfo.setSummaryInit(true);
                	}
                	break;
                case XmlPullParser.START_DOCUMENT:
                case XmlPullParser.END_DOCUMENT:
                default:
                	break;
	        	}
	        	
	        	eventType = parser.next();
	        }
    	} catch ( Throwable tr ) {
    	}
    	return appInfo;
	}
}