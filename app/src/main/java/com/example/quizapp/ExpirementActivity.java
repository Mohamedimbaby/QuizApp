package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ExpirementActivity extends AppCompatActivity {
    Spinner answerSpinner;
    TextView QuestionText ,timerText,scoreText;
    Button startButton, nextButton,timerButton;
    MediaPlayer mediaPlayer ;
    byte Cancelstime,roletimes=0;

    String [] questions ={"egypt","us","uk","france"};
    String [] capitals  ={"cairo","ws","london","paris"};
    byte index=0,score;
    ArrayList<String> items=new ArrayList<>();

    SharedPreferences preferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expirement);
        answerSpinner= findViewById(R.id.answersspinner);
        QuestionText= findViewById(R.id.questiontText);
        scoreText= findViewById(R.id.scoreText);
        timerText= findViewById(R.id.timertext);
        startButton= findViewById(R.id.startButton);
        nextButton= findViewById(R.id.nextButton);
        timerButton= findViewById(R.id.timerButton);

        preferences=getPreferences(MODE_PRIVATE);


            int last=preferences.getInt("score",-1);
      int count=preferences.getInt(""+last,-1);

        if (count>-1)
        scoreText.setText("ur  get this score "+last+" for  "+count+" times before ");




    }

    @Override
    public void onBackPressed() {

        if(mediaPlayer!=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        SharedPreferences.Editor editor= preferences.edit();

        int last=preferences.getInt(""+score,0);

            editor.putInt(""+score, last + 1);
            editor.putInt("score",score);

            editor.commit();

        super.onBackPressed();
    }

    public void timerStart(View view) {
        mediaPlayer = MediaPlayer.create(this, R.raw.wrong);
        mediaPlayer.start();

    }

    public void next(View view)
    {
        String answer = answerSpinner.getSelectedItem().toString();
        answerSpinner.setSelection(0);

        if(answer.equals("please select"))
        {
            Cancelstime++;
            if (Cancelstime==3)
            {Cancelstime=0;
            index++;
        }
        }

       else {
           if(answer.equals(capitals[index]))
                        {
                            score++;
                            items.remove(answer);
                        }
                    Collections.shuffle(items.subList(1,items.size()));
                    index++;
                          if(index<capitals.length)
                            {
                                QuestionText.setText("what is the capital of "+questions[index] +"?");

                            timer.cancel();
                            timer.start();

                            }
                          else {
                              timer.cancel();
                              nextButton.setEnabled(false);

            if(score>=3)
            {
                mediaPlayer = MediaPlayer.create(this, R.raw.right);

            }
            else {
                mediaPlayer = MediaPlayer.create(this, R.raw.wrong);

            }
                              mediaPlayer.start();
                              Toast.makeText(this, " your score is "+score, Toast.LENGTH_SHORT).show();

                              SharedPreferences.Editor editor= preferences.edit();
                              int last=preferences.getInt(""+score,0);



                                  scoreText.setText("ur  get this score "+score+" for  "+(last+1)+" times before ");



                          }
             }
    }

    CountDownTimer timer = new CountDownTimer(10000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timerText.setText(String.valueOf(millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {
            index++;
            if(index<questions.length) {
                QuestionText.setText("what is the capital of " + questions[index] + "?");
                timerText.setText("Error");
                timer.start();
            }
            else {
                nextButton.setEnabled(false);

             getResult();

          timer.cancel();
                 }

        }
    };
    void getResult()
    {

        Toast.makeText(this, " your scorsssssse is "+score, Toast.LENGTH_SHORT).show();
        if(score>=3) {
            mediaPlayer = MediaPlayer.create(this, R.raw.right);

        }
        else mediaPlayer=MediaPlayer.create(this,R.raw.wrong);

        mediaPlayer.start();
    }
    public void start(View view)
    {
        QuestionText.setText("what is the capital of "+questions[0] +"?");
        items.clear();
        Collections.addAll(items,"please select",
                "cairo",
                "damascus",
                "algeria",
                "ws",
                "tokio",
                "paris",
                "moscow",
                "london");
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,items);
        answerSpinner.setAdapter(adapter);
        index=0;score=0;
        nextButton.setEnabled(true);


        timer.start();



    }
}
