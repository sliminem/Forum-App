// This class handles the parsing of the XML document provided by google weather
// and extracts the relevant onformation from it.

package com.android.fapp;
    
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

     
public class WeatherHandler extends DefaultHandler{

	// the necessary fields for opening and closing tags
    private boolean in_temp = false;
    private boolean in_cond = false;

    private boolean in_day1 = false;
    private boolean in_day2 = false;
    private boolean in_day3 = false;
	private boolean in_day4 = false;
	private boolean in_day5 = false;
	
	private boolean in_cond1 = false;
	private boolean in_cond2 = false;
	private boolean in_cond3 = false;
	private boolean in_cond4 = false;
	
	
    private boolean in_icon1 = false;
    private boolean in_icon2 = false;
    private boolean in_icon3 = false;
    private boolean in_icon4 = false;
    private boolean in_icon5 = false;

    public static String temp;
    public static String cond= "";
    public static String[] days = {"a","a","a","a"};
    public static String[] icons = {"","","","",""};
    public static String[] conds = {"","","",""};
    
    @Override
    public void startDocument() throws SAXException {
    	
    }
     
    @Override
    public void endDocument() throws SAXException {
    
    }
     
    // Code executed when and opening tag is reached
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
    	Log.w("Opening Tag", "info");
    	if(localName.equals("temp_c")){
    		this.in_temp=true;
    		temp = atts.getValue("data");
    	}else if(localName.equals("condition") && cond==""){
    		this.in_cond=true;
    		cond = atts.getValue("data");
    	}else if(localName.equals("day_of_week") && days[0]=="a"){
    		this.in_day1=true;
    		String day1 = atts.getValue("data");
    		days[0] = day1;
    	}else if(localName.equals("day_of_week") && days[1]=="a"){
    		this.in_day2=true;
    		String day2 = atts.getValue("data");
    		days[1] = day2;
    	}else if(localName.equals("day_of_week") && days[2]=="a"){
    		this.in_day3=true;
    		String day3 = atts.getValue("data");
    		days[2] = day3;
    	}else if(localName.equals("day_of_week") && days[3]=="a"){
    		this.in_day4=true;
    		String day4 = atts.getValue("data");
    		days[3] = day4;
    	}else if(localName.equals("icon") && icons[0]==""){
    		this.in_icon1=true;
    		icons[0] = atts.getValue("data");
    	}else if(localName.equals("icon") && icons[1]==""){
    		this.in_icon2=true;
    		icons[1] = atts.getValue("data");
    	}else if(localName.equals("icon") && icons[2]==""){
    		this.in_icon3=true;
    		icons[2] = atts.getValue("data");
    	}else if(localName.equals("icon") && icons[3]==""){
    		this.in_icon4=true;
    		icons[3] = atts.getValue("data");
    	}else if(localName.equals("icon") && icons[4]==""){
    		this.in_icon5=true;
    		icons[4] = atts.getValue("data");
    	}else if(localName.equals("condition") && conds[0]==""){
    		this.in_cond1=true;
    		conds[0] = atts.getValue("data");
    	}else if(localName.equals("condition") && conds[1]==""){
    		this.in_cond2=true;
    		conds[1] = atts.getValue("data");
    	}else if(localName.equals("condition") && conds[2]==""){
    		this.in_cond3=true;
    		conds[2] = atts.getValue("data");
    	}else if(localName.equals("condition") && conds[3]==""){
    		this.in_cond4=true;
    		conds[3] = atts.getValue("data");
    	}
    	
    	Log.w("TEST_1",days[0]+" "+days[1]+" "+days[2]);
                   
    }
           
         
    // code executen when a closing tag is reached
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    		throws SAXException {
    			if(localName.equals("temp_c")){
    				this.in_temp=false;
            	}else if(localName.equals("condition")&&cond==""){
            		this.in_cond=false;
            	}else if(localName.equals("day_of_week") && days[0]!="a"){
            		this.in_day1=false;
            	}else if(localName.equals("day_of_week") && days[1]!="a"){
            		this.in_day2=false;
            	}else if(localName.equals("day_of_week") && days[2]!="a"){
            		this.in_day3=false;
            	}else if(localName.equals("day_of_week") && days[3]!="a"){
            		this.in_day4=false;
            	}else if(localName.equals("icon") && icons[0]!=""){
            		this.in_icon1=false;
            	}else if(localName.equals("icon") && icons[1]!=""){
            		this.in_icon2=false;
            	}else if(localName.equals("icon") && icons[2]!=""){
            		this.in_icon3=false;
            	}else if(localName.equals("icon") && icons[3]!=""){
            		this.in_icon4=false;
            	}else if(localName.equals("icon") && icons[4]!=""){
            		this.in_icon5=false;
            	}else if(localName.equals("condition") && icons[0]!=""){
            		this.in_cond1=false;
            	}else if(localName.equals("condition") && icons[1]!=""){
            		this.in_cond2=false;
            	}else if(localName.equals("condition") && icons[2]!=""){
            		this.in_cond3=false;
            	}else if(localName.equals("condition") && icons[3]!=""){
            		this.in_cond4=false;
            	}
    }
           
    // Code executed when inbetween opening and closing tags
    @Override
    public void characters(char ch[], int start, int length) {
    	
    }
}