
package com.example.act3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;


public class ReservaActivity extends AppCompatActivity {

    private EditText etPlazas;
    private CheckBox cbCondiciones, cbCertificado, cbMaterial;
    private Button btnAnterior, btnLimpiar, btnRevisar;
    private Spinner spTaller;
    private RadioGroup rgModalidad;
    private RadioButton rbPresencial, rbOnline;
    private DatePicker dpFecha;
    private TimePicker tpHora;

    private SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);

        etPlazas = findViewById(R.id.etPlazas);
        cbCondiciones = findViewById(R.id.cbCondiciones);
        cbCertificado = findViewById(R.id.cbCertificado);
        cbMaterial = findViewById(R.id.cbMaterial);
        spTaller = findViewById(R.id.spTaller);
        rgModalidad = findViewById(R.id.rgModalidad);
        rbPresencial = findViewById(R.id.rbPresencial);
        rbOnline = findViewById(R.id.rbOnline);
        dpFecha = findViewById(R.id.dpFecha);
        tpHora = findViewById(R.id.tpHora);
        btnAnterior = findViewById(R.id.btnAnterior);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        btnRevisar = findViewById(R.id.btnRevisar);

        tpHora.setIs24HourView(true);

        preferencias = getSharedPreferences("Data", MODE_PRIVATE);

        // Restore Spinner selection if it was saved previously
        String tallerGuardado = preferencias.getString("taller", "");

        for (int i = 0; i < spTaller.getCount(); i++) {

            if (spTaller.getItemAtPosition(i).toString()
                    .equals(tallerGuardado)) {

                spTaller.setSelection(i);
                break;
            }
        }

        //Restore modalidad
        String modalidadGuardada = preferencias.getString("modalidad", "");

        if (modalidadGuardada.equals("Presencial")) {
            rbPresencial.setChecked(true);
        } else if (modalidadGuardada.equals("Online")) {
            rbOnline.setChecked(true);
        }

        //Restore plazas
        String plazasGuardadas =
                preferencias.getString("plazas", "");

        etPlazas.setText(plazasGuardadas);

        //Restore checkbox
        cbMaterial.setChecked(preferencias.getBoolean("material", false));
        cbCertificado.setChecked(preferencias.getBoolean("certificado", false));
        cbCondiciones.setChecked(preferencias.getBoolean("condiciones", false));

        // Restore DatePicker
        if (preferencias.contains("fechaAnio")) {

            int anioGuardado =
                    preferencias.getInt("fechaAnio", dpFecha.getYear());

            int mesGuardado =
                    preferencias.getInt("fechaMes", dpFecha.getMonth());

            int diaGuardado =
                    preferencias.getInt("fechaDia", dpFecha.getDayOfMonth());

            dpFecha.updateDate(
                    anioGuardado,
                    mesGuardado,
                    diaGuardado
            );
        }

        // Restore TimePicker
        if (preferencias.contains("horaH")) {

            int horaGuardada =
                    preferencias.getInt("horaH", tpHora.getHour());

            int minutoGuardado =
                    preferencias.getInt("horaM", tpHora.getMinute());

            tpHora.setHour(horaGuardada);
            tpHora.setMinute(minutoGuardado);
        }

        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReservaActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        btnRevisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Spinner: position 0 is NOT a valid workshop
                if (spTaller.getSelectedItemPosition() == 0) {
                    Toast.makeText(
                            ReservaActivity.this,
                            "Selecciona un taller",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                // A modalidad must be selected
                if (!rbPresencial.isChecked() && !rbOnline.isChecked()) {
                    Toast.makeText(
                            ReservaActivity.this,
                            "Selecciona una modalidad",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                // Plazas must be between 1 and 10
                String textoPlazas =
                        etPlazas.getText().toString().trim();

                if (!textoPlazas.matches("[1-9]|10")) {
                    etPlazas.setError(
                            "Las plazas deben estar entre 1 y 10"
                    );
                    return;
                }

                // Conditions must be accepted
                if (!cbCondiciones.isChecked()) {
                    Toast.makeText(
                            ReservaActivity.this,
                            "Debes aceptar las condiciones de inscripción",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                //GET VALUES AFTER VALIDATION
                String taller =
                        spTaller.getSelectedItem().toString();

                String modalidad;

                if (rbPresencial.isChecked()) {
                    modalidad = "Presencial";
                } else {
                    modalidad = "Online";
                }

                boolean material =
                        cbMaterial.isChecked();

                boolean certificado =
                        cbCertificado.isChecked();


                // DatePicker values
                int dia =
                        dpFecha.getDayOfMonth();

                int mes =
                        dpFecha.getMonth();

                int anio =
                        dpFecha.getYear();

                // +1 only for the text shown in the summary
                String fecha = dia + "/" + (mes + 1) + "/" + anio;


                // TimePicker values
                int horaSeleccionada =
                        tpHora.getHour();

                int minuto =
                        tpHora.getMinute();

                String hora;

                if (minuto < 10) {
                    hora = horaSeleccionada + ":0" + minuto;
                } else {
                    hora = horaSeleccionada + ":" + minuto;
                }

                // SAVE DATA WITH SHAREDPREFERENCES
                SharedPreferences.Editor editor =
                        preferencias.edit();

                editor.putString("taller", taller);
                editor.putString("modalidad", modalidad);
                editor.putString("plazas", textoPlazas);

                editor.putBoolean("material", material);
                editor.putBoolean("certificado", certificado);
                editor.putBoolean(
                        "condiciones",
                        cbCondiciones.isChecked()
                );

                // Text values for Activity 3
                editor.putString("fecha", fecha);
                editor.putString("hora", hora);

                // Numeric values for restoring Activity 2 later
                editor.putInt("fechaAnio", anio);
                editor.putInt("fechaMes", mes);
                editor.putInt("fechaDia", dia);

                editor.putInt(
                        "horaH",
                        horaSeleccionada
                );

                editor.putInt(
                        "horaM",
                        minuto
                );

                editor.apply();


                // Open Activity 3
                Intent i = new Intent(
                        ReservaActivity.this,
                        ResumenActivity.class
                );

                startActivity(i);
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset controls
                spTaller.setSelection(0);

                rgModalidad.clearCheck();

                etPlazas.setText("");
                etPlazas.setError(null);

                cbMaterial.setChecked(false);
                cbCertificado.setChecked(false);
                cbCondiciones.setChecked(false);

                // Return DatePicker and TimePicker to current date/time
                Calendar ahora =
                        Calendar.getInstance();

                dpFecha.updateDate(
                        ahora.get(Calendar.YEAR),
                        ahora.get(Calendar.MONTH),
                        ahora.get(Calendar.DAY_OF_MONTH)
                );

                tpHora.setHour(
                        ahora.get(Calendar.HOUR_OF_DAY)
                );

                tpHora.setMinute(
                        ahora.get(Calendar.MINUTE)
                );


                // Delete ONLY Activity 2 SharedPreferences
                preferencias.edit()
                        .remove("taller")
                        .remove("modalidad")
                        .remove("plazas")
                        .remove("material")
                        .remove("certificado")
                        .remove("condiciones")
                        .remove("fecha")
                        .remove("hora")
                        .remove("fechaAnio")
                        .remove("fechaMes")
                        .remove("fechaDia")
                        .remove("horaH")
                        .remove("horaM")
                        .apply();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.llReserva), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}