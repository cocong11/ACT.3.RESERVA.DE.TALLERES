package com.example.act3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText etNombre, etCorreo, etTelefono;
    private Switch swNotificaciones;
    private CheckBox cbInformacion;
    private Button btnContinuar, btnLimpiar;

    private SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etTelefono = findViewById(R.id.etTelefono);
        swNotificaciones = findViewById(R.id.swNotificaciones);
        cbInformacion = findViewById(R.id.cbInformacion);
        btnContinuar = findViewById(R.id.btnContinuar);
        btnLimpiar = findViewById(R.id.btnLimpiar);

        preferencias = getSharedPreferences("Data", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        etNombre.setText(preferencias.getString("nombre", ""));
        etCorreo.setText(preferencias.getString("correo", ""));
        etTelefono.setText(preferencias.getString("telefono", ""));
        swNotificaciones.setChecked(preferencias.getBoolean(
                        "notificaciones", false));
        cbInformacion.setChecked(preferencias.getBoolean(
                        "informacion", false));


        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString().trim();
                String correo = etCorreo.getText().toString().trim();
                String telefono = etTelefono.getText().toString().trim();

                boolean valido = true;

                if (nombre.isEmpty()) {
                    etNombre.setError("Introduce tu nombre");
                    valido = false;
                } else {
                    etNombre.setError(null);
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                    etCorreo.setError("Correo no válido");
                    valido = false;
                } else {
                    etCorreo.setError(null);
                }

                if (telefono.isEmpty()) {
                    etTelefono.setError("Introduce tu teléfono");
                    valido = false;
                } else {
                    etTelefono.setError(null);
                }

                if (!valido) {
                    return;
                }

                editor.putString("nombre", nombre);
                editor.putString("correo", correo);
                editor.putString("telefono", telefono);
                editor.putBoolean("notificaciones",swNotificaciones.isChecked());
                editor.putBoolean("informacion",cbInformacion.isChecked());

                editor.apply();

                Intent i = new Intent(MainActivity.this,ReservaActivity.class);
                startActivity(i);
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNombre.setText("");
                etCorreo.setText("");
                etTelefono.setText("");

                swNotificaciones.setChecked(false);
                cbInformacion.setChecked(false);

                // Borrar solamente los datos de Activity 1
                // Delete only the data for Activity 1(this activity)
                preferencias.edit()
                        .remove("nombre")
                        .remove("correo")
                        .remove("telefono")
                        .remove("notificaciones")
                        .remove("informacion")
                        .apply();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}