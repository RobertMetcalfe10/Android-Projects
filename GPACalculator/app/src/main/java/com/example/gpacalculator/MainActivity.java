package com.example.gpacalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public Double converter(String grade)
    {
        grade=grade.toUpperCase();
            switch (grade)
            {
                case "A+": return 4.2;
                case "A":  return 4.0;
                case "A-": return 3.8;
                case "B+": return 3.6;
                case "B":  return 3.4;
                case "B-": return 3.2;
                case "C+": return 3.0;
                case "C":  return 2.8;
                case "C-": return 2.6;
                case "D+": return 2.4;
                case "D":  return 2.2;
                case "D-": return 2.0;
                case "E":  return 1.6;
                case "F":  return 1.0;
                case "G":  return 0.4;
                case "NG": return 0.0;

            }
        return 0.0;

    }

    public void calculate(View V)
    {

        TextView but1=(TextView) findViewById(R.id.editText1);
        TextView but2=(TextView) findViewById(R.id.editText2);
        TextView but3=(TextView) findViewById(R.id.editText3);
        TextView but4=(TextView) findViewById(R.id.editText4);
        TextView but5=(TextView) findViewById(R.id.editText5);
        TextView but6=(TextView) findViewById(R.id.editText6);
        TextView but7=(TextView) findViewById(R.id.editText7);
        TextView but8=(TextView) findViewById(R.id.editText8);
        TextView but9=(TextView) findViewById(R.id.editText9);
        TextView but10=(TextView)findViewById(R.id.editText10);
        TextView but11=(TextView)findViewById(R.id.editText11);
        TextView but12=(TextView)findViewById(R.id.editText12);

        TextView GPA=(TextView)findViewById(R.id.GPA);
        GPA.setAlpha(1f);
        double gpa=0.0;
        int count=0;
        for(int i=1;i<=12;i++)
        {
            switch (i)
            {
                case 1:
                    if(converter(but1.getText().toString())!=0.0)
                    {
                        count++;
                        gpa+=converter(but1.getText().toString());
                    }
                    break;
                case 2:
                    if(converter(but2.getText().toString())!=0.0)
                    {
                        count++;
                        gpa+=converter(but2.getText().toString());
                    }
                    break;
                case 3:
                    if(converter(but3.getText().toString())!=0.0)
                    {
                        count++;
                        gpa+=converter(but3.getText().toString());
                    }
                    break;
                case 4:
                    if(converter(but4.getText().toString())!=0.0)
                    {
                        count++;
                        gpa+=converter(but4.getText().toString());
                    }
                    break;
                case 5:
                    if(converter(but5.getText().toString())!=0.0)
                    {
                        count++;
                        gpa+=converter(but5.getText().toString());
                    }
                    break;
                case 6:
                    if(converter(but6.getText().toString())!=0.0)
                    {
                        count++;
                        gpa+=converter(but6.getText().toString());
                    }
                    break;
                case 7:
                    if(converter(but7.getText().toString())!=0.0)
                    {
                        count++;
                        gpa+=converter(but7.getText().toString());
                    }
                    break;
                case 8:
                    if(converter(but8.getText().toString())!=0.0)
                    {
                        count++;
                        gpa+=converter(but8.getText().toString());
                    }
                    break;
                case 9:
                    if(converter(but9.getText().toString())!=0.0)
                    {
                        count++;
                        gpa+=converter(but9.getText().toString());
                    }
                    break;
                case 10:
                    if(converter(but10.getText().toString())!=0.0)
                    {
                        count++;
                        gpa+=converter(but10.getText().toString());
                    }
                    break;
                case 11:
                    if(converter(but11.getText().toString())!=0.0)
                    {
                        count++;
                        gpa+=converter(but11.getText().toString());
                    }
                    break;
                case 12:
                    if(converter(but12.getText().toString())!=0.0)
                    {
                        count++;
                        gpa+=converter(but12.getText().toString());
                    }
                    break;

            }
        }

        GPA.setText("GPA="+new DecimalFormat("#.#").format(gpa/count));
        System.out.println(gpa+" "+count);
        gpa=0.0;
        count=0;
        GPA.animate().alpha(0).setDuration(5000);

    }


}
