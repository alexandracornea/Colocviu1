package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button pressMeButton, pressMeTooButton, navigateToSecondaryActivityButton;
    EditText leftEditText, rightEditText;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pressMeButton = findViewById(R.id.press_me_button);
        pressMeTooButton = findViewById(R.id.press_me_too_button);
        navigateToSecondaryActivityButton = findViewById(R.id.navigate_to_secondary_activity_button);
        leftEditText = findViewById(R.id.left_edit_text);
        rightEditText = findViewById(R.id.right_edit_text);

        leftEditText.setText("0");
        rightEditText.setText("0");

        pressMeButton.setOnClickListener(v -> {
            String leftCount = leftEditText.getText().toString();
            int left = Integer.parseInt(leftCount);
            left++;
            leftEditText.setText(String.valueOf(left));
        });
        pressMeTooButton.setOnClickListener(v -> {
            String rightCount = rightEditText.getText().toString();
            int right = Integer.parseInt(rightCount);
            right++;
            rightEditText.setText(String.valueOf(right));
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Toast.makeText(this, "The activity returned with result " + result.getData().getIntExtra(Constants.SUM, -1), Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "The activity returned with result RESULT_CANCELED", Toast.LENGTH_LONG).show();
            }
        });
        navigateToSecondaryActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, PracticalTest01SecondaryActivity.class);
            int sum = Integer.parseInt(leftEditText.getText().toString()) + Integer.parseInt(rightEditText.getText().toString());
            intent.putExtra(Constants.SUM, sum);
            activityResultLauncher.launch(intent);
        });

        // navigateToSecondaryActivityButton.setOnClickListener(v -> {
        //     int sum = Integer.parseInt(leftEditText.getText().toString()) + Integer.parseInt(rightEditText.getText().toString());
        //     Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
        //     intent.putExtra(Constants.SUM, sum);
        //     startActivity(intent);
        // });

        Intent intent = new Intent(this, PracticalTest01Service.class);
        intent.putExtra(Constants.LEFT_COUNT, leftEditText.getText().toString());
        intent.putExtra(Constants.RIGHT_COUNT, rightEditText.getText().toString());
        // startService(intent);
        startForegroundService(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Constants.LEFT_COUNT, leftEditText.getText().toString());
        outState.putString(Constants.RIGHT_COUNT, rightEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey(Constants.LEFT_COUNT)) {
            leftEditText.setText(savedInstanceState.getString(Constants.LEFT_COUNT));
        } else {
            leftEditText.setText("0");
        }

        if (savedInstanceState.containsKey(Constants.RIGHT_COUNT)) {
            rightEditText.setText(savedInstanceState.getString(Constants.RIGHT_COUNT));
        } else {
            rightEditText.setText("0");
        }
    }
}