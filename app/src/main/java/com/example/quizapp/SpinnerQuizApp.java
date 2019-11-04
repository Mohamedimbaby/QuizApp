package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class SpinnerQuizApp extends AppCompatActivity {
    Spinner answerSpinner;
    TextView QuestionText ,timerText;
    Button startButton, nextButton,timerButton;
    MediaPlayer mediaPlayer ;
    byte Cancelstime,roletimes=0;
    String [] questions ={"egypt","us","uk","france"};
    String [] capitals  ={"cairo","ws","london","paris"};
    byte index=0,score=0;
    ArrayList<String> items=new ArrayList<>();
    ArrayList<Integer> scores=new ArrayList<>();
    SharedPreferences preferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_quiz_app);
        answerSpinner= findViewById(R.id.answersspinner);
        QuestionText= findViewById(R.id.questiontText);
        timerText= findViewById(R.id.timertext);
        startButton= findViewById(R.id.startButton);
        nextButton= findViewById(R.id.nextButton);
        timerButton= findViewById(R.id.timerButton);

        preferences=getPreferences(MODE_PRIVATE);
        int last=preferences.getInt("score",-1);

            timerText.setText("ur  max score is "+last);




    }

    @Override
    public void onBackPressed() {
        if(mediaPlayer!=null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        SharedPreferences.Editor editor= preferences.edit();
        Toast.makeText(this, ""+score, Toast.LENGTH_SHORT).show();
        editor.putInt("score",score);
        editor.commit();
        super.onBackPressed();
    }

    public void next(View view) {

        String answer = answerSpinner.getSelectedItem().toString();

        answerSpinner.setSelection(0);
        Collections.shuffle(items.subList(1,items.size()));
        if(!answer.isEmpty())
        {
            if(answer.equals(capitals[index]))
            {score++;
            items.remove(answer);
            }
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

            scores.add((int) score);
            if(score>=3) mediaPlayer= MediaPlayer.create(this,R.raw.right);
            else  mediaPlayer= MediaPlayer.create(this,R.raw.wrong);
            mediaPlayer.start();
            nextButton.setEnabled(false);

        }
    }

    public void start(View view) {
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

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,items);
        answerSpinner.setAdapter(adapter);
        QuestionText.setText("what is the capital of "+questions[0]);

        score=0;
        index=0;
        if(nextButton.isEnabled())
        {
            ++roletimes;

            if(roletimes==2) finish();
        }

        nextButton.setEnabled(true);

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
