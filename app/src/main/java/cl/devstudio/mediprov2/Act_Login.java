package cl.devstudio.mediprov2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import cl.devstudio.mediprov2.Model.Paciente;
import cl.devstudio.mediprov2.Model.Profesional;

public class Act_Login extends AppCompatActivity implements View.OnClickListener {
    Button btn_log;
    EditText et_email, et_password;
    TextView txt_register, txt_recovery;
    CheckBox cb_saveSession;
    String email, password, resultMd5;
    Profesional profesional = new Profesional();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        // Inicialización elementos
        et_email = findViewById(R.id.login_email);
        et_password = findViewById(R.id.login_password);
        txt_register = findViewById(R.id.login_to_Register);
        txt_recovery = findViewById(R.id.login_to_Recovery);
        cb_saveSession = findViewById(R.id.login_checkbox);
        btn_log = findViewById(R.id.btn_login);

        //Inicializacion de button´s
        btn_log.setOnClickListener(this);
        txt_register.setOnClickListener(this);

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.login_to_Register:
                Intent i = new Intent(Act_Login.this,Act_Register.class);
                startActivity(i);
                finish();
                break;
            case R.id.login_to_Recovery:

                break;
            case R.id.login_checkbox:

                break;
        }

    }


    public void login() {
        email = et_email.getText().toString().trim();
        password = et_password.getText().toString();
        int i = 0;
        if (email.isEmpty()) {

            Toast.makeText(this, "Campo vacio", Toast.LENGTH_SHORT).show();
        } else {
            i++;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Campo vacio", Toast.LENGTH_SHORT).show();
        } else {
            i++;
        }
        if (i == 2) {

            resultMd5 = getMd5(password);

            db.collection("profesional")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    if (document.getString("password").equals(resultMd5)) {
                                     Intent i = new Intent(Act_Login.this,Act_bottom_nav.class);
                                     startActivity(i);
                                     finish();

                                    } else {
                                        Toast.makeText(Act_Login.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(Act_Login.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        }


    }



    public String getMd5(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // digest() method is called to calculate message digest
        //  of an input digest() return array of byte
        byte[] messageDigest = md.digest(password.getBytes());

        // Convert byte array into signum representation
        BigInteger no = new BigInteger(1, messageDigest);

        // Convert message digest into hex value
        String hashtext = no.toString(16);

        return hashtext;

    }
}


