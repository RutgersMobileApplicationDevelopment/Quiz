package rutgers.edu.quiz;

import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

class CheatActivity extends AppCompatActivity {
    private Button mCheatButton;
    private TextView mAnswerText;
    public static final String EXTRA_ANSWER="CheatActivity.EXTRA_ANSWER";

    public static Intent getIntent(Context c, String answer){
        Intent i= new Intent(c,CheatActivity.class);
        i.putExtra(EXTRA_ANSWER,answer);
        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mCheatButton=findViewById(R.id.button_cheat);
        mAnswerText=findViewById(R.id.textview_answer);

        String answer=getIntent().getStringExtra(EXTRA_ANSWER);
        mAnswerText.setText(answer);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheatButton.animate().alphaBy(-100).setDuration(5000).start();
            }
        });

    }
}
