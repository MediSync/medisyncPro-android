package cl.devstudio.mediprov2;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.widget.TextView;

import cl.devstudio.mediprov2.Frag_Bottom.Frag_Ajustes;
import cl.devstudio.mediprov2.Frag_Bottom.Frag_Cuenta;
import cl.devstudio.mediprov2.Frag_Bottom.Frag_listaPaciente;

public class Act_bottom_nav extends AppCompatActivity {
    Fragment miFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bottom_nav);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        miFragment = new Frag_listaPaciente();
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container_fragment,miFragment).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_listPatient:
                    miFragment = new Frag_listaPaciente();
                    break;
                case R.id.navigation_dashboard:
                    miFragment = new Frag_Ajustes();
                    break;
                case R.id.navigation_Account:
                    miFragment = new Frag_Cuenta();
                    break;
            }
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.container_fragment,miFragment).commit();
            return true;
        }
    };



}
