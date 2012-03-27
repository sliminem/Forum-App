//Using Google Weather API
//http://www.google.co.uk/ig/api?weather=Edinburgh

package com.android.fapp;
 
import java.io.InputStream;
import java.net.URL;
 
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
 
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
 
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
 
public class EdiWeather extends Activity {
       
	//define icons for the weather conditions
        Drawable dicon1;
        Drawable dicon2;
        Drawable dicon3;
        Drawable dicon4;
        Drawable dicon5;
 
        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle icicle) {
                super.onCreate(icicle);                
                setContentView(R.layout.weather);
                setTitle("Informatics Forum App - Weather");

                
                //Today's temperature, icon and weather conditions
                TextView today_cond = (TextView) findViewById(R.id.today_cond);
                TextView today_temp = (TextView) findViewById(R.id.today_temp);
                ImageView today_icon = (ImageView) findViewById(R.id.today_icon);
                
                //Setting the forecast
                TextView tv1 = (TextView) findViewById(R.id.day1);
                TextView tv2 = (TextView) findViewById(R.id.day2);
                TextView tv3 = (TextView) findViewById(R.id.day3);                                
                TextView tv4 = (TextView) findViewById(R.id.day4);
                
                //Setting the conditions TextViews
                TextView cd1 = (TextView) findViewById(R.id.cond1);
                TextView cd2 = (TextView) findViewById(R.id.cond2);
                TextView cd3 = (TextView) findViewById(R.id.cond3);                                
                TextView cd4 = (TextView) findViewById(R.id.cond4);
                
                //Setting the weather icons for the forecast
                ImageView icon1 = (ImageView) findViewById(R.id.icon1);
                ImageView icon2 = (ImageView) findViewById(R.id.icon2);
                ImageView icon3 = (ImageView) findViewById(R.id.icon3);
                ImageView icon4 = (ImageView) findViewById(R.id.icon4);             
                

                try {
                        /* Create a URL we want to load some xml-data from. */
                        URL url = new URL("http://www.google.co.uk/ig/api?weather=Edinburgh");
 
                        /* Get a SAXParser from the SAXPArserFactory. */
                        SAXParserFactory spf = SAXParserFactory.newInstance();
                        SAXParser sp = spf.newSAXParser();
 
                        /* Get the XMLReader of the SAXParser we created. */
                        XMLReader xr = sp.getXMLReader();
                        /* Create a new ContentHandler and apply it to the XML-Reader*/
                        WeatherHandler myExampleHandler = new WeatherHandler();
                        xr.setContentHandler(myExampleHandler);
                       
                        /* Parse the xml-data from our URL. */
                        xr.parse(new InputSource(url.openStream()));
                        /* Parsing has finished. */
 
                        /* Our ExampleHandler now provides the parsed data to us. */
                        //ParsedExampleDataSet parsedExampleDataSet =myExampleHandler.getParsedData();
 
                        /* Set the result to be displayed in our GUI. */
                        today_temp.setText(WeatherHandler.temp+"°C");
                        today_cond.setText(WeatherHandler.cond);
                        tv1.setText(WeatherHandler.days[0]);
                        tv2.setText(WeatherHandler.days[1]);
                        tv3.setText(WeatherHandler.days[2]);
                        tv4.setText(WeatherHandler.days[3]);
                        
                        cd1.setText(WeatherHandler.conds[0]);
                        cd2.setText(WeatherHandler.conds[1]);
                        cd3.setText(WeatherHandler.conds[2]);
                        cd4.setText(WeatherHandler.conds[3]);

                        
                        dicon1 = LoadImageFromWeb("http://www.google.co.uk"+WeatherHandler.icons[0]);
                        dicon2 = LoadImageFromWeb("http://www.google.co.uk"+WeatherHandler.icons[1]);
                        dicon3 = LoadImageFromWeb("http://www.google.co.uk"+WeatherHandler.icons[2]);
                        dicon4 = LoadImageFromWeb("http://www.google.co.uk"+WeatherHandler.icons[3]);
                        dicon5 = LoadImageFromWeb("http://www.google.co.uk"+WeatherHandler.icons[4]);
                        
                        today_icon.setImageDrawable(dicon1);
                        icon1.setImageDrawable(dicon2);
                        icon2.setImageDrawable(dicon3);
                        icon3.setImageDrawable(dicon4);
                        icon4.setImageDrawable(dicon5);
                        
                } catch (Exception e) {
                        /* Display any Error to the GUI. */
                        tv1.setText("Error: " + e.getMessage());
                        Log.e("Exception", "WeatherQueryError", e);
                }
                
        }

		private Drawable LoadImageFromWeb(String uri) {
			try
			{
				InputStream is = (InputStream) new URL(uri).getContent();
				Drawable d = Drawable.createFromStream(is, "src name");
				return d;
			}catch (Exception e) {
				System.out.println("Exception = "+e);
				return null;
			}
		}
}
