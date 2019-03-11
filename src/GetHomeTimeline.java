/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import twitter4j.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.7
 */
public class GetHomeTimeline {
    /**
     * Usage: java twitter4j.examples.timeline.GetHomeTimeline
     *
     * @param args String[]
     * @throws IOException 
     */ 
	
    public static void main(String[] args) throws IOException {
        try {
        	Date date = new Date();
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
        	File file = new File(dateFormat.format(date) + ".xml") ;
        	PrintWriter pw = new PrintWriter(file);
            StringBuilder sb = new StringBuilder();
            BufferedReader bufReader = new BufferedReader(new FileReader("Testate.txt"));
            Twitter twitter = new TwitterFactory().getInstance();
            Paging p = new Paging();
            p.setCount(200);
            String line = bufReader.readLine();
            sb.append("<?xml version=\"1.0\"?>\n");
            sb.append("<TWEETS>\n");
	        while((line = bufReader.readLine()) != null) {
	        	List<Status> statuses = twitter.getUserTimeline(line,p);
	            for (Status status : statuses) {
	            	sb.append("\t<singleTweet>\n");
	            		sb.append("\t\t<id>" + status.getId() + "</id>\n");
		            	sb.append("\t\t<author>" + line + "</author>\n");
		                sb.append("\t\t<text>" + status.getText() + "</text>\n");
		                sb.append("\t\t<createdAt>" + status.getCreatedAt() + "</createdAt>\n");
		                sb.append("\t\t<favoriteCount>" + status.getFavoriteCount() + "</favoriteCount>\n");             
		                sb.append("\t\t<retweetCount>" + status.getRetweetCount() + "</retweetCount>\n");
	                sb.append("\t</singleTweet>\n");
	            }
	        }
	        bufReader.close();
            sb.append("</TWEETS>");
            pw.write(sb.toString());
            pw.close();
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }
}