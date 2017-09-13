package com.example.parseserverinstagram;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.share_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.Share)
        {
            //ask for permission
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
            else
            {
                getPhoto();
            }
        }

        if(item.getItemId()==R.id.Logout)
        {
            ParseUser.logOut();

            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }



    public void getPhoto()
    {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1&&resultCode==RESULT_OK&&data!=null)
        {
            Uri selectedImage=data.getData();

            try {
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                System.out.println("photo recieved");

                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte[] byteArray=stream.toByteArray();

                ParseFile file=new ParseFile("image.PNG",byteArray);

                ParseObject object=new ParseObject("Images");
                object.put("image",file);
                object.put("username",ParseUser.getCurrentUser().getUsername());

                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e==null)
                        {
                            Toast.makeText(UserListActivity.this,"Image Shared",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(UserListActivity.this,"Image Not Shared",Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1)
        {
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                getPhoto();
            }
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setTitle("User Feed");

        final ArrayList<String> users=new ArrayList<>();

        final ListView listView=(ListView)findViewById(R.id.userList);


        final ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,users);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(getApplicationContext(), UserFeedActivity.class);
                intent.putExtra("usernames",users.get(position));
                startActivity(intent);
            }
        });


        ParseQuery<ParseUser> query=ParseUser.getQuery();

        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

        query.addAscendingOrder("username");

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if(e==null)
                {
                    if(objects.size()>0)
                    {
                        for(ParseUser user:objects)
                        {
                            users.add(user.getUsername());
                        }

                        listView.setAdapter(arrayAdapter);

                    }
                }
                else
                {
                    e.printStackTrace();
                }
            }
        });


    }
}
