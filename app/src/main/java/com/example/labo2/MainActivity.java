package com.example.labo2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etHeight, etWeight;
    private RadioGroup rgGender;
    private CheckBox cbSaveData;
    private Button btnCalculate;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        rgGender = findViewById(R.id.rgGender);
        cbSaveData = findViewById(R.id.cbSaveData);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });
    }

    private void calculateBMI() {

        if (!cbSaveData.isChecked()) {
            Toast.makeText(this, "Musisz zaznaczyć 'Zapisz moje dane', aby zobaczyć wynik!", Toast.LENGTH_SHORT).show();
            tvResult.setText("");
            return;
        }

        String heightStr = etHeight.getText().toString().trim();
        String weightStr = etWeight.getText().toString().trim();

        if (heightStr.isEmpty()) {
            etHeight.setError("Wprowadź wzrost!");
            return;
        }
        if (weightStr.isEmpty()) {
            etWeight.setError("Wprowadź wagę!");
            return;
        }

        try {
            float heightCm = Float.parseFloat(heightStr);
            float weightKg = Float.parseFloat(weightStr);

            if (heightCm <= 0 || weightKg <= 0) {
                Toast.makeText(this, "Wartości muszą być większe od zera", Toast.LENGTH_SHORT).show();
                return;
            }

            float heightM = heightCm / 100;
            float bmi = weightKg / (heightM * heightM);

            displayResult(bmi);


        } catch (NumberFormatException e) {
            Toast.makeText(this, "Nieprawidłowy format liczb", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayResult(float bmi) {
        String category;
        if (bmi < 18.5) {
            category = "Niedowaga";
        } else if (bmi < 25) {
            category = "Waga prawidłowa";
        } else if (bmi < 30) {
            category = "Nadwaga";
        } else {
            category = "Otyłość";
        }

        String resultText = String.format(Locale.getDefault(), "Twoje BMI: %.2f\nInterpretacja: %s", bmi, category);
        tvResult.setText(resultText);
    }
}
