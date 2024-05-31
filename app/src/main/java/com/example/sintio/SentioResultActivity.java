package com.example.sintio;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SentioResultActivity extends AppCompatActivity {

    public static final String EXTRA_EMOTION = "extra_emotion";
    public static final String EXTRA_PERCENTAGE = "extra_percentage";
    public static final String EXTRA_DESCRIPTION = "extra_description";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sentio_result);

        // Obtener datos de la actividad anterior
        String emotion = getIntent().getStringExtra(EXTRA_EMOTION);
        int percentage = getIntent().getIntExtra(EXTRA_PERCENTAGE, 0);
        String description = getIntent().getStringExtra(EXTRA_DESCRIPTION);

        // Mostrar datos en la interfaz
        TextView textViewEmotion = findViewById(R.id.textViewEmotion);
        TextView textViewPercentage = findViewById(R.id.textViewPercentage);
        TextView textViewDescription = findViewById(R.id.textViewDescription);

        textViewEmotion.setText(emotion);
        textViewPercentage.setText(percentage + "%");
        textViewDescription.setText(description);
    }
}
