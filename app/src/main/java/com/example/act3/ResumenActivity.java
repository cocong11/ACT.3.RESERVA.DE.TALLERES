package com.example.act3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResumenActivity extends AppCompatActivity {

    private TextView tvNombre, tvCorreo, tvTelefono;
    private TextView tvNotificaciones, tvInformacion;
    private TextView tvTaller, tvModalidad, tvPlazas;
    private TextView tvMaterial, tvCertificado;
    private TextView tvFecha, tvHora;

    private Button btnVolver, btnEnviar;

    private SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);


        tvNombre = findViewById(R.id.tvNombre);
        tvCorreo = findViewById(R.id.tvCorreo);
        tvTelefono = findViewById(R.id.tvTelefono);
        tvNotificaciones = findViewById(R.id.tvNotificaciones);
        tvInformacion = findViewById(R.id.tvInformacion);
        tvTaller = findViewById(R.id.tvTaller);
        tvModalidad = findViewById(R.id.tvModalidad);
        tvPlazas = findViewById(R.id.tvPlazas);
        tvMaterial = findViewById(R.id.tvMaterial);
        tvCertificado = findViewById(R.id.tvCertificado);

        tvFecha = findViewById(R.id.tvFecha);
        tvHora = findViewById(R.id.tvHora);
        btnVolver = findViewById(R.id.btnVolver);
        btnEnviar = findViewById(R.id.btnEnviar);

        preferencias = getSharedPreferences("Data", MODE_PRIVATE);
        
        String nombre = preferencias.getString("nombre", "");
        String correo = preferencias.getString("correo", "");
        String telefono = preferencias.getString("telefono", "");
        boolean notificaciones = preferencias.getBoolean("notificaciones", false);
        boolean informacion = preferencias.getBoolean("informacion", false);
        tvNombre.setText("Nombre: " + nombre);
        tvCorreo.setText("Correo: " + correo);
        tvTelefono.setText("Teléfono: " + telefono);

        if (notificaciones) {
            tvNotificaciones.setText("Notificaciones: Activadas");
        } else {
            tvNotificaciones.setText("Notificaciones: Desactivadas");
        }

        if (informacion) {
            tvInformacion.setText("Información de talleres: Sí");
        } else {
            tvInformacion.setText("Información de talleres: No");
        }

        String taller = preferencias.getString("taller", "");
        String modalidad = preferencias.getString("modalidad", "");
        String plazas = preferencias.getString("plazas", "");
        boolean material = preferencias.getBoolean("material", false);
        boolean certificado = preferencias.getBoolean("certificado", false);
        String fecha = preferencias.getString("fecha", "");
        String hora = preferencias.getString("hora", "");

        tvTaller.setText("Taller: " + taller);
        tvModalidad.setText("Modalidad: " + modalidad);
        tvPlazas.setText("Plazas: " + plazas);

        if (material) {
            tvMaterial.setText("Material: Sí");
        } else {
            tvMaterial.setText("Material: No");
        }

        if (certificado) {
            tvCertificado.setText("Certificado: Sí");
        } else {
            tvCertificado.setText("Certificado: No");
        }

        tvFecha.setText("Fecha: " + fecha);
        tvHora.setText("Hora: " + hora);


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(
                        ResumenActivity.this,
                        "Reserva enviada correctamente",
                        Toast.LENGTH_LONG
                ).show();

            }
        });


    }
}
