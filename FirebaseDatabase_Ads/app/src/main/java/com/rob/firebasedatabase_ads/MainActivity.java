package com.rob.firebasedatabase_ads;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
// ...

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private AdView mAdView;
    // ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //database
        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference();
        Map<String,String> values=new HashMap<>();
        values.put("name","rob");
        dbref.push().setValue(values, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError==null)
                {
                    Toast.makeText(getApplicationContext(),"Save Succesful",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Save Unsuccesful",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
