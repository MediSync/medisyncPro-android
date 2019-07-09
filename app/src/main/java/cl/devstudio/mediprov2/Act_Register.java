package cl.devstudio.mediprov2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Act_Register extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    EditText et_birth_date, et_rut, et_names, et_last_name1,
            et_last_name2, et_Address, et_phone, et_password, et_email;
    String rut, names, last_name1, last_name2, birthdate, email, address, phone, password;
    Spinner comboSex;
    ImageButton toolbar_back;
    Button btn_registrar;
    TextView txt_title;
    String sexo = "null", title = "Registrar";
    ArrayList<String> listdatos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);
        //Inicialización de elementos
        et_rut = findViewById(R.id.register_rut);
        et_names = findViewById(R.id.register_names);
        et_last_name1 = findViewById(R.id.register_last_name1);
        et_last_name2 = findViewById(R.id.register_last_name2);
        et_email = findViewById(R.id.register_email);
        et_Address = findViewById(R.id.register_address);
        et_phone = findViewById(R.id.register_phone);
        et_password = findViewById(R.id.register_password);
        btn_registrar = findViewById(R.id.btn_Register);

        et_birth_date = findViewById(R.id.register_birth_date);
        comboSex = findViewById(R.id.reg_spinnerSex);
        txt_title = findViewById(R.id.toolbar_back);
        toolbar_back = findViewById(R.id.toolbar_back);
        setToolbar();


        //Inicialización de button´s
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.combo_sex, android.R.layout.simple_spinner_item);
        comboSex.setAdapter(adapter);
        et_birth_date.setOnClickListener(this);
        btn_registrar.setOnClickListener(this);
        toolbar_back.setOnClickListener(this);
        comboSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                if (parent.getItemAtPosition(i).toString().equals("Masculino")) {
                    sexo = "M";
                }
                if (parent.getItemAtPosition(i).toString().equals("Femenino")) {
                    sexo = "F";
                }
                if (parent.getItemAtPosition(i).toString().equals("Sexo...")) {
                    sexo = "null";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void registarProfesional() {
        int a = 0;
        rut = et_rut.getText().toString().trim();
        names = et_names.getText().toString().trim();
        last_name1 = et_last_name1.getText().toString().trim();
        last_name2 = et_last_name2.getText().toString().trim();
        birthdate = et_birth_date.getText().toString().trim();
        address = et_Address.getText().toString().trim();
        phone = et_phone.getText().toString().trim();
        email = et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();

        if (!rut.isEmpty()) { a++; }

        if (!names.isEmpty()) { a++; }

        if (!last_name1.isEmpty()) { a++; }

        if (!last_name2.isEmpty()) { a++; }

        if (!birthdate.isEmpty()) { a++; }

        if (!address.isEmpty()) { a++; }

        if (!phone.isEmpty()) { a++; }

        if (!email.isEmpty()) { a++; }

        if (!password.isEmpty()) { a++; }

        if (!sexo.equals("null")) { a++; }

        if (a==10){
            Toast.makeText(this, "muy bien", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(Act_Register.this,
                    "Verifique los datos", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_birth_date:
                showDatePickerDialog();
                break;
            case R.id.toolbar_back:
                Intent i = new Intent(Act_Register.this, Act_Login.class);
                startActivity(i);
                finish();
                break;
            case R.id.btn_Register:
                registarProfesional();
                break;
        }
    }

    public void setToolbar() {
        txt_title.setText(title);
    }

    private void showDatePickerDialog() {
        DatePickerDialog dateDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        dateDialog.show();

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        try {
            month++;
            String birth_date = day + "/" + month + "/" + year;
            SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
            Date date = format.parse(birth_date);
            String stringDate = new SimpleDateFormat("dd/mm/yyyy").format(date);


            et_birth_date.setText(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}