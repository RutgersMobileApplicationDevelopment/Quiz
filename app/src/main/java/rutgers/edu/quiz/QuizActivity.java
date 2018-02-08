package rutgers.edu.quiz;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

class QuizActivity extends AppCompatActivity{

    private static final String EXTRA_INDEX="QuizActivity.EXTRA_INDEX";

    private static final String[][] mQuestions={
            {"Austin","Texas","True"},
            {"Los Angeles","New Jersey","False"},
            {"Providence","Rhode Island","True"},
            {"Sacramento","California","True"},
            {"Cary","North Carolina","False"},
            {"Houston","Washington","False"}
    };

    private ImageButton mLeftButton,mRightButton;
    private TextView mQuestionTextView;
    private Button mTrueButton, mFalseButton;

    private int mIndex;
    private int mWrongCount=0;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mIndex=0;
        if(savedInstanceState!=null){
            mIndex=savedInstanceState.getInt(EXTRA_INDEX,0);
        }

        mLeftButton=findViewById(R.id.button_left);
        mRightButton=findViewById(R.id.button_right);
        mQuestionTextView=findViewById(R.id.textview_question);
        mTrueButton=findViewById(R.id.button_true);
        mFalseButton=findViewById(R.id.button_false);

        mQuestionTextView.setText(getQuestion(mQuestions[mIndex][0],mQuestions[mIndex][1]));
        mQuestionTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i=CheatActivity.getIntent(QuizActivity.this,mQuestions[mIndex][2]);
                QuizActivity.this.startActivity(i);
                return true;
            }
        });

        mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIndex==0){
                    mIndex=mQuestions.length-1;
                }else{
                    mIndex--;
                }
                mWrongCount=0;
                mQuestionTextView.setText(getQuestion(mQuestions[mIndex][0],mQuestions[mIndex][1]));
            }
        });

        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIndex==mQuestions.length-1){
                    mIndex=0;
                }else{
                    mIndex++;
                }
                mWrongCount=0;
                mQuestionTextView.setText(getQuestion(mQuestions[mIndex][0],mQuestions[mIndex][1]));
            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res=mQuestions[mIndex][2];
                if(res.equals("True")){
                    Toast.makeText(QuizActivity.this,"Correct!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(QuizActivity.this,"Wrong",Toast.LENGTH_SHORT).show();
                    mWrongCount++;
                    if(mWrongCount>2){
                        showHintMessage();
                    }
                }
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res=mQuestions[mIndex][2];
                if(res.equals("False")){
                    Toast.makeText(QuizActivity.this,"Correct!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(QuizActivity.this,"Wrong!",Toast.LENGTH_SHORT).show();
                    mWrongCount++;
                    if(mWrongCount>2){
                        showHintMessage();
                    }
                }

            }
        });
    }

    private String getQuestion(String capital, String state){
        return capital+" is the capital of "+state;
    }

    private void showHintMessage(){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.constraintlayout_quiz),
                "Want a hint?", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Yes", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri location = Uri.parse("geo:0,0?q="+mQuestions[mIndex][1]);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                if(doesAppExist(mapIntent)){
                    startActivity(mapIntent);
                }
            }
        });
        snackbar.show();
    }

    private boolean doesAppExist(Intent intent){
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return activities.size() > 0;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_INDEX,mIndex);
    }
}
