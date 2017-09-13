package com.rob.instantmessenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class profile extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;

    DatabaseReference db=FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-901c1.firebaseio.com/messages");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, login.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //displaying logged in user name
        textViewUserEmail.setText("Welcome "+user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(this);


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                check();
            }
        }, 0, 2000);


    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
        if(view == buttonLogout){
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, login.class));
        }
    }

    public void messageSend(View v)
    {
        EditText et=(EditText)findViewById(R.id.enterText);
        String message=firebaseAuth.getCurrentUser().getEmail()+"\n"+et.getText().toString();
        db.push().setValue(message);
        check();
        et.setText("");
        Toast.makeText(this,"Message Sent!",Toast.LENGTH_SHORT).show();

    }



    public void check()
    {
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String,Object> newPost=new HashMap<>();
                ArrayList messages=new ArrayList();

                for(DataSnapshot datasnapshot : dataSnapshot.getChildren()){
                    System.out.println(datasnapshot.getValue().toString());
//                        newPost=(Map<String,Object>)datasnapshot.getValue();
//                        String name = String.valueOf(newPost.get("user"));
//                        String message = String.valueOf(newPost.get("message"));

                    newPost.put(firebaseAuth.getCurrentUser().getEmail(),datasnapshot.getValue().toString());

                    messages.add(datasnapshot.getValue().toString());

                }

                ArrayAdapter<String> itemsAdapter=new ArrayAdapter<String>(profile.this,android.R.layout.simple_list_item_1,messages);
                ListView listView = (ListView) findViewById(R.id.messageList);

                listView.setAdapter(itemsAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }

}