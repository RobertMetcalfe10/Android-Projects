package com.example.moviefinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    ImageView downloading;
    String imageSt;
    String title;
    String date;
    String overview;
    String voteAverage;

    public void downloadImage()
    {
        ImageDownloader task=new ImageDownloader();
        Bitmap image;

        try {
            image=task.execute("http://image.tmdb.org/t/p/w185//lZpWprJqbIFpEV5uoHfoK0KCnTW.jpg").get();
            downloading.setImageBitmap(image);
        } catch (Exception e) {
        }
    }

    public void downloadMovie(View view)
    {
        EditText ET=(EditText)findViewById(R.id.EnterMovie);
        DownLoadTask task=new DownLoadTask();
        task.execute("https://api.themoviedb.org/3/search/movie?api_key=d21b5a699128e614cc8acba3b62cdc73&query="+ ET.getText());
    }

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    downloading=(ImageView)findViewById(R.id.downloading);


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
                String movieInfo=jsonObject.getString("results");
                JSONArray jsonArray=new JSONArray(movieInfo);
                System.out.println(jsonArray);


                    JSONObject jsonObj=jsonArray.getJSONObject(0);
                    title=jsonObj.getString("title");
                    date=jsonObj.getString("release_date");
                    imageSt=jsonObj.getString("poster_path");
                    overview=jsonObj.getString("overview");
                    voteAverage=jsonObj.getString("vote_average");

                Log.i("image",imageSt);
                downloadImage();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public class ImageDownloader extends AsyncTask<String,Void,Bitmap>{

        @Override
        public Bitmap doInBackground(String... urls)
        {
            try {
                URL url=new URL(urls[0]);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.connect();
                InputStream inputStream=connection.getInputStream();
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                Log.i("image","worked");
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
