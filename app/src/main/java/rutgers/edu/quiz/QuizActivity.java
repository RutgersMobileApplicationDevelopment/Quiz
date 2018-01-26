package rutgers.edu.quiz;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

class QuizActivity extends AppCompatActivity{

    private static final String EXTRA_INDEX="QuizActivity.EXTRA_INDEX";

    private static final String[][] mQuestions={
            {"Austin is the capital of Texas","True"},
            {"Los Angeles is the capital of New Jersey","False"},
            {"Providence is the capital of Rhode Island","True"},
            {"Sacramento is the capital of California","True"},
            {"Cary is the capital of North Carolina","False"},
            {"Houston is the capital of Washington","False"}
    };

    private ImageButton mLeftButton,mRightButton;
    private TextView mQuestionTextView;
    private Button mTrueButton, mFalseButton;

    private int mIndex;
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

        mQuestionTextView.setText(mQuestions[mIndex][0]);
        mQuestionTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i=CheatActivity.getIntent(QuizActivity.this,mQuestions[mIndex][1]);
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
                mQuestionTextView.setText(mQuestions[mIndex][0]);
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
                mQuestionTextView.setText(mQuestions[mIndex][0]);
            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res=mQuestions[mIndex][1];
                if(res.equals("True")){
                    Toast.makeText(QuizActivity.this,"Correct!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(QuizActivity.this,"Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res=mQuestions[mIndex][1];
                if(res.equals("False")){
                    Toast.makeText(QuizActivity.this,"Correct!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(QuizActivity.this,"Wrong!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_INDEX,mIndex);
    }
}
