package com.example.robert.xo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    //Users
    User Player1=new User(true);
    User Player2=new User(false);
    int Player1Score=0;
    int Player2Score=0;
    int[] gameState={0,0,0,0,0,0,0,0,0};


    public void restart(View view)
    {
        TextView Tv=(TextView)findViewById(R.id.WinnerMessage);
        Tv.animate().alpha(0f).setDuration(1000);
        Button butt=(Button)findViewById(view.getId());
        butt.animate().alpha(0f).setDuration(1000);
        butt.setClickable(false);
        for(int i=0; i<gameState.length;i++)
        {
            gameState[i]=0;
        }

        ImageView iv4=(ImageView) findViewById(R.id.imageView4);
        ImageView iv5=(ImageView) findViewById(R.id.imageView5);
        ImageView iv6=(ImageView) findViewById(R.id.imageView6);
        ImageView iv7=(ImageView) findViewById(R.id.imageView7);
        ImageView iv8=(ImageView) findViewById(R.id.imageView8);
        ImageView iv9=(ImageView) findViewById(R.id.imageView9);
        ImageView iv10=(ImageView) findViewById(R.id.imageView10);
        ImageView iv11=(ImageView) findViewById(R.id.imageView11);
        ImageView iv12=(ImageView) findViewById(R.id.imageView12);

        iv4.setAlpha(0f);
        iv5.setAlpha(0f);
        iv6.setAlpha(0f);
        iv7.setAlpha(0f);
        iv8.setAlpha(0f);
        iv9.setAlpha(0f);
        iv10.setAlpha(0f);
        iv11.setAlpha(0f);
        iv12.setAlpha(0f);

        TextView Sc1=(TextView) findViewById((R.id.Player1Score));
        TextView Sc2=(TextView) findViewById((R.id.Player2Score));
        TextView S1=(TextView) findViewById((R.id.Score1));
        TextView S2=(TextView) findViewById((R.id.Score2));
        Sc1.animate().alpha(1f).setDuration(1000);
        Sc2.animate().alpha(1f).setDuration(1000);
        S1.animate().alpha(1f).setDuration(1000);
        S2.animate().alpha(1f).setDuration(1000);

    }

    public void won(String colour)
    {
        boolean reset=false;
        String user="";
        if(colour=="black")
        {
            Player2Score++;
            TextView S2=(TextView) findViewById((R.id.Score2));
            S2.setText(Integer.toString(Player2Score));
            user="Player 2, ";
        }
        else if(colour=="red")
        {
            Player1Score++;
            TextView S1=(TextView) findViewById((R.id.Score1));
            S1.setText(Integer.toString(Player1Score));
            user="Player 1, ";
        }
        else
        {
            TextView Tv=(TextView)findViewById(R.id.WinnerMessage);
            Tv.animate().alpha(1f).setDuration(1000);
            Tv.setText("It's a draw");
            Button butt=(Button)findViewById(R.id.playAgain);
            butt.animate().alpha(1f).setDuration(1000);
            butt.setClickable(true);
            reset=true;

        }
        if(!reset)
        {
            TextView Tv=(TextView)findViewById(R.id.WinnerMessage);
            Tv.animate().alpha(1f).setDuration(1000);
            Tv.setText("Well done "+user+"you won");
            Button butt=(Button)findViewById(R.id.playAgain);
            butt.animate().alpha(1f).setDuration(1000);
            butt.setClickable(true);
        }


        TextView Sc1=(TextView) findViewById((R.id.Player1Score));
        TextView Sc2=(TextView) findViewById((R.id.Player2Score));
        TextView S1=(TextView) findViewById((R.id.Score1));
        TextView S2=(TextView) findViewById((R.id.Score2));
        Sc1.animate().alpha(0f).setDuration(1000);
        Sc2.animate().alpha(0f).setDuration(1000);
        S1.animate().alpha(0f).setDuration(1000);
        S2.animate().alpha(0f).setDuration(1000);
    }

    public void checkWin()
    {

        int play;
        String colour="";
        if(Player1.turn)
        {
            play=1;
            colour="red";
        }
        else
        {
            play=2;
            colour="black";
        }
        if(gameState[0]==play&&gameState[1]==play&&gameState[2]==play)
        {
            System.out.println(colour);
            won(colour);
            return;
        }
        if(gameState[3]==play&&gameState[4]==play&&gameState[5]==play)
        {
            System.out.println(colour);
            won(colour);
            return;
        }
        if(gameState[6]==play&&gameState[7]==play&&gameState[8]==play)
        {
            System.out.println(colour);
            won(colour);
            return;
        }
        if(gameState[0]==play&&gameState[3]==play&&gameState[6]==play)
        {
            System.out.println(colour);
            won(colour);
            return;
        }
        if(gameState[1]==play&&gameState[4]==play&&gameState[7]==play)
        {
            System.out.println(colour);
            won(colour);
            return;
        }
        if(gameState[2]==play&&gameState[5]==play&&gameState[8]==play)
        {
            System.out.println(colour);
            won(colour);
            return;
        }
        if(gameState[0]==play&&gameState[4]==play&&gameState[8]==play)
        {
            System.out.println(colour);
            won(colour);
            return;
        }
        if(gameState[2]==play&&gameState[4]==play&&gameState[6]==play)
        {
            System.out.println(colour);
            won(colour);
            return;
        }

        int check=1;
        for(int i=0;i<gameState.length;i++)
        {
            if(gameState[i]==0)
            {
                check=gameState[i];
            }
        }
        if(check!=0)
        {
            won("white");
        }

    }

    public void update(View view)
    {

        ImageView IV=(ImageView) findViewById(view.getId());
        int t=Integer.parseInt(view.getTag().toString());
        if(Player1.turn&&gameState[t]==0)
        {
            gameState[t]=1;
            IV.animate().alpha(1f);
            IV.setImageResource(R.drawable.redcircle);
            checkWin();
            Player1.turn=false;
            Player2.turn=true;
        }
        else if(Player2.turn&&gameState[t]==0)
        {
            gameState[t]=2;
            IV.animate().alpha(1f);
            IV.setImageResource(R.drawable.blackcircle);
            checkWin();
            Player2.turn=false;
            Player1.turn=true;
        }


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
