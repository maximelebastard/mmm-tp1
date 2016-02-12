package istic.edu.mmtp1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import istic.edu.mmtp1.formdata.SignInFormData;

public class ResultActivity extends AppCompatActivity {

    // Components
    TextView txtNom;
    TextView txtNaissance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Components
        txtNom = (TextView) findViewById(R.id.txtNom);
        txtNaissance = (TextView) findViewById(R.id.txtNaissance);

        // Get form content
        SignInFormData formdata = (SignInFormData) getIntent().getParcelableExtra(MainActivity.EXTRA_FORM_MESSAGE);

        // Put form content in view
        String[] resultNameArgs = {formdata.getNom(),formdata.getPrenom()};
        String resultName = String.format(getResources().getString(R.string.result_name), resultNameArgs);

        String[] resultNaissanceArgs = {formdata.getVilleNaissance(),formdata.getRegionNumber(),formdata.getFormatedDateNaissance()};
        String resultNaissance = String.format(getResources().getString(R.string.result_ne), resultNaissanceArgs);

        txtNom.setText(resultName);
        txtNaissance.setText(resultNaissance);

    }

}
