/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.example.parseserverinstagram;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import static android.widget.Toast.LENGTH_SHORT;


public class MainActivity extends AppCompatActivity {

public boolean check=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("InstaAndroid");

        TextView pass=(TextView)findViewById(R.id.Password);

        pass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(keyCode==KeyEvent.KEYCODE_ENTER)
                {
                    check=true;

                    CreateUser(findViewById(R.id.signup));
                }

                return false;
            }
        });

        if(ParseUser.getCurrentUser()!=null)
        {
            showUserList();
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }

    public void CreateUser(View view)
    {
        Button button=(Button)findViewById(R.id.signup);
        final TextView username=(TextView)findViewById(R.id.Username);
        final TextView pass=(TextView)findViewById(R.id.Password);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                ParseUser user=new ParseUser();

                    user.setUsername(username.getText().toString());
                    user.setPassword(pass.getText().toString());
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null)
                            {
                                Toast.makeText(getApplication().getBaseContext(),"Sign Up Successful",LENGTH_SHORT).show();
                                showUserList();
                            }
                            else if(check)
                            {
                                Login(findViewById(R.id.login));
                                check=false;
                            }
                            else
                            {
                                Toast.makeText(getApplication().getBaseContext(),"Sign Up Unsuccessful",LENGTH_SHORT).show();
                            }

                        }
                    });
                }

        });
    }

    public void Login(View view)
    {
        final TextView username=(TextView)findViewById(R.id.Username);
        final TextView pass=(TextView)findViewById(R.id.Password);


        ParseUser.logInInBackground(username.getText().toString(), pass.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {

              if(user!=null)
              {
                  Toast.makeText(getApplication().getBaseContext(),"Logged In",LENGTH_SHORT).show();
                  showUserList();
              }
              else
              {
                  Toast.makeText(getApplication().getBaseContext(),"Invalid Username/Password",LENGTH_SHORT).show();
              }
          }
      });
    }


    public void showUserList()
    {
        Intent intent=new Intent(getApplicationContext(),UserListActivity.class);
        startActivity(intent);
    }

//      //new score in score class
//      final ParseObject score=new ParseObject("score");
//
////      score.put("username","tiar");
////      score.put("score",100);
//      score.saveInBackground(new SaveCallback() {
//          @Override
//          public void done(ParseException e) {
//
//              if(e==null)
//              {
//                  System.out.println("successful");
//                  System.out.println(score.getJSONObject("tiar"));
//              }
//              else
//              {
//                  System.out.println("failed"+e.toString());
//              }
//          }
//      });


//      ParseQuery<ParseObject> query=ParseQuery.getQuery("score");
//      query.getInBackground("6eA2Z1AKHu", new GetCallback<ParseObject>() {
//          @Override
//          public void done(ParseObject object, ParseException e) {
//              if(e==null&&object!=null)
//              {
//                  System.out.println(object.getString("username"));
//                  System.out.println(object.getInt("score"));
//                  System.out.println(object.getClassName());
//              }
//              else
//              {
//                  System.out.println("failed"+e.toString());
//              }
//          }
//      });


//Query
//      ParseQuery<ParseObject> query=ParseQuery.getQuery("score");
//      query.whereGreaterThan("score",0);
//
//      query.findInBackground(new FindCallback<ParseObject>() {
//          @Override
//          public void done(List<ParseObject> objects, ParseException e) {
//
//              if(e==null)
//              {
//                  System.out.println(objects.size());
//                  if(objects.size()>0)
//                  {
//                      for(ParseObject object:objects)
//                      {
//                          System.out.println(object.getInt("score"));
//                      }
//                  }
//              }
//
//          }
//      });




//create user
//      ParseUser user=new ParseUser();
//      user.setUsername("userR");
//      user.setPassword("pass");
//      user.setEmail("robert@monfrancais.net");
//
//      user.signUpInBackground(new SignUpCallback() {
//          @Override
//          public void done(ParseException e) {
//
//              if(e==null)
//              {
//                  System.out.println("Sign up successful");
//              }
//
//          }
//      });

    //log in
//      ParseUser.logInInBackground("userR", "pass", new LogInCallback() {
//          @Override
//          public void done(ParseUser user, ParseException e) {
//
//              if(user!=null)
//              {
//                  System.out.println("Successful");
//              }
//              else
//              {
//                  System.out.println("Failed "+e.toString());
//              }
//          }
//      });

//check user
//      if(ParseUser.getCurrentUser()!=null)
//      {
//          System.out.println("logged in="+ParseUser.getCurrentUser().getUsername());
//      }
//      else
//      {
//          System.out.println("not logged in");
//      }

//log out
//      ParseUser.logOut();




}