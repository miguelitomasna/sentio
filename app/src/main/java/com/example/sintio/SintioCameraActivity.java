package com.example.sintio;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SintioCameraActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private boolean isInForeground;

    private ImageView imageViewSeleccionada;
    private View selectorArchivoLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sintio_camera);

        // Marcamos que la actividad está en primer plano
        isInForeground = true;

        imageViewSeleccionada = findViewById(R.id.imageViewSeleccionada);
        selectorArchivoLayout = findViewById(R.id.selectorArchivoLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isInForeground = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isInForeground = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isInForeground) {
            // No hacemos nada si el botón de retroceso es presionado
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void goBackToMainActivity(View view) {
        finish(); // Finaliza la actividad actual
    }

    public void selectFile(View view) {
        // Mostrar un AlertDialog con el mensaje de advertencia
        new AlertDialog.Builder(this)
                .setTitle("Advertencia")
                .setMessage("Por favor, seleccione una foto que muestre su cara o perfil para calificar su estado de humor. No seleccione una foto que no sea de un rostro.")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Abrir el selector de archivos
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    public void calificarEstado(View view) {
        // Declarar variables para almacenar los resultados
        String emotion = "";
        int percentage = 0;
        String description = "";

        // Generar un número aleatorio para determinar la emoción y el porcentaje
        double random = Math.random() * 100;

        if (random >= 70) {
            // Emoción: Feliz
            emotion = "Feliz";
            percentage = (int) (Math.random() * (100 - 70 + 1) + 70); // Entre 70 y 100%

            description = "El sujeto parece estar contento y sonriente. " +
                    "La expresión facial indica un estado emocional positivo. " +
                    "Los ojos están brillantes y la sonrisa es amplia. " +
                    "Es posible que la persona esté disfrutando de un momento feliz. " +
                    "Puede haber una risa contagiosa y gestos relajados. " +
                    "La postura puede ser abierta y relajada, indicando confort y satisfacción. " +
                    "Quizás esté compartiendo historias alegres o experiencias positivas.";

        } else if (random >= 40) {
            // Emoción: Triste
            emotion = "Triste";
            percentage = (int) (Math.random() * (70 - 40 + 1) + 40); // Entre 40 y 70%

            description = "El sujeto parece estar triste y con el ánimo bajo. " +
                    "La expresión facial muestra una mirada melancólica y los ojos pueden estar entrecerrados. " +
                    "La boca está caída y puede haber señales de tensión en la frente. " +
                    "Es probable que la persona esté experimentando sentimientos de pesar o desánimo. " +
                    "Puede haber suspiros profundos y un tono de voz bajo. " +
                    "Es posible que prefiera estar solo y evitar interacciones sociales.";

        } else {
            // Emoción: Renegado
            emotion = "Renegado";
            percentage = (int) (Math.random() * (40 - 0 + 1)); // Entre 0 y 40%

            description = "El sujeto parece estar molesto o enfadado. " +
                    "La expresión facial es tensa, con cejas fruncidas y una mirada intensa. " +
                    "Puede haber muestras de incomodidad o irritación en los gestos. " +
                    "Es posible que la persona esté sintiendo frustración, enojo o disgusto. " +
                    "Puede haber gestos bruscos y una postura cerrada. " +
                    "Puede que evite el contacto visual y prefiera el silencio.";

        }

        // Crear el intent para pasar a la actividad de resultados
        Intent intent = new Intent(this, SentioResultActivity.class);
        intent.putExtra(SentioResultActivity.EXTRA_EMOTION, emotion);
        intent.putExtra(SentioResultActivity.EXTRA_PERCENTAGE, percentage);
        intent.putExtra(SentioResultActivity.EXTRA_DESCRIPTION, description);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            loadImageWithRoundedBorder(uri);
        }
    }

    private void loadImageWithRoundedBorder(Uri imageUri) {
        // Ajustar la imagen con borde redondeado
        RoundedImageView roundedImageView = findViewById(R.id.imageViewSeleccionada);
        roundedImageView.setImageURI(imageUri);

        // Mostrar la imagen y ocultar el texto de selección
        roundedImageView.setVisibility(View.VISIBLE);
        selectorArchivoLayout.findViewById(R.id.txtSelectorArchivos).setVisibility(View.GONE);
    }
}
