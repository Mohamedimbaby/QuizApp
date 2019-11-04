package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText answerEdit;
TextView QuestionText ,timerText;
Button startButton, nextButton,timerButton;
MediaPlayer mediaPlayer ;
byte Cancelstime,roletimes=0;
String [] questions ={"egypt","us","uk","france"};
    String [] capitals  ={"cairo","wu","london","paris"};
    byte index=0,score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        answerEdit= findViewById(R.id.answerEdit);
        QuestionText= findViewById(R.id.questiontText);
        timerText= findViewById(R.id.timertext);
        startButton= findViewById(R.id.startButton);
        nextButton= findViewById(R.id.nextButton);

        timerButton= findViewById(R.id.timerButton);








    }

    @Override
    public void onBackPressed() {
if(mediaPlayer!=null) {
    mediaPlayer.stop();
    mediaPlayer.release();
}
super.onBackPressed();
    }

    public void next(View view) {

        String answer = answerEdit.getText().toString();
        answerEdit.setText("");
        if(!answer.isEmpty())
        {

            if(answer.equals(capitals[index]))
            {score++;}
            index++;
            if(index<questions.length){


                QuestionText.setText("what is the capital of "+questions[index]);
            }
        }

        else {
            Cancelstime++;
            Toast.makeText(this,String.valueOf(Cancelstime),Toast.LENGTH_LONG).show();
            if(Cancelstime==3)
            {

                    Cancelstime=0;
                    index++;
                if(index<questions.length){


                    QuestionText.setText("what is the capital of "+questions[index]);
                }
            }
        }

        if(index>= questions.length) {
            Toast.makeText(this,String.valueOf(score),Toast.LENGTH_LONG).show();
            if(score>=3) mediaPlayer= MediaPlayer.create(this,R.raw.right);
            else  mediaPlayer= MediaPlayer.create(this,R.raw.wrong);
            mediaPlayer.start();
            nextButton.setEnabled(false);
            answerEdit.setEnabled(false);
        }
    }

    public void start(View view) {
    QuestionText.setText("what is the capital of "+questions[0]);

    score=0;
    index=0;
    if(nextButton.isEnabled())
    {
        ++roletimes;

        if(roletimes==2) finish();
    }

    nextButton.setEnabled(true);
    answerEdit.setEnabled(true);
    }

    public void timerStart(View view) {

         CountDownTimer timer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {


                timerText.setText(String.valueOf(millisUntilFinished/1000));

            }

            @Override
            public void onFinish() {

            }
        };
            timer.start();
    }
}
