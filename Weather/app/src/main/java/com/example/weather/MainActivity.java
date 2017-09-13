package com.example.weather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {



    public void click(View view)
    {
        EditText city=(EditText)findViewById(R.id.CityName);
        String name=city.getText().toString();

        DownLoadTask task=new DownLoadTask();

        task.execute("http://api.openweathermap.org/data/2.5/weather?q="+name+"&appid=3d84bc1ea829662733e82c9267a11cb8");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }




    public class DownLoadTask extends AsyncTask<String,Void,String>
    {
        @Override
        public String doInBackground(String... urls)
        {
            String result="";
            URL url;
            HttpURLConnection httpURLConnection=null;

            try {
                url=new URL(urls[0]);
                httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream in=httpURLConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);

                int data = reader.read();

                while(data!=-1)
                {
                    char current=(char)data;
                    result+=current;
                    data=reader.read();
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onPostExecute(String result)
        {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject=new JSONObject(result);
                JSONObject detailsTemp=jsonObject.getJSONObject("main");
                JSONObject detailsWind=jsonObject.getJSONObject("wind");
                JSONArray detailsWeather=jsonObject.getJSONArray("weather");

                String weather="Weather=";

                for(int i=0;i<detailsWeather.length();i++)
                {
                    JSONObject jsonObj=detailsWeather.getJSONObject(i);
                    weather+=jsonObj.getString("description");
                }

                String temp=detailsTemp.getString("temp");
                double a=Double.parseDouble(temp);
                a-=273.00;
                temp="Temp="+String.format( "%.2f", a );
                String speed="Speed="+detailsWind.getString("speed")+"\nWind direction="+detailsWind.getString("deg");
                weather+="\n"+temp+"\n"+speed;

                TextView txt=(TextView)findViewById(R.id.Results);
                txt.setText(weather);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
