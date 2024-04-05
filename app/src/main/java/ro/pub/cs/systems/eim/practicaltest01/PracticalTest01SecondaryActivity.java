package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    Button okButton, cancelButton;
    TextView sumTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        okButton = findViewById(R.id.ok_button);
        cancelButton = findViewById(R.id.cancel_button);
        sumTextView = findViewById(R.id.sum);

        Intent intent = getIntent();
        if (intent != null && Objects.requireNonNull(intent.getExtras()).containsKey(Constants.SUM)) {
            int sum = intent.getIntExtra(Constants.SUM, -1);
            sumTextView.setText(String.valueOf(sum));
        }

        okButton.setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra(Constants.SUM, Integer.parseInt(sumTextView.getText().toString()));
            setResult(RESULT_OK, result);
            finish();
        });

        cancelButton.setOnClickListener(v -> {
            setResult(RESULT_CANCELED, new Intent());
            finish();
        });
    }
}