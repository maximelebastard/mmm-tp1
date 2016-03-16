package istic.edu.mmmtp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import istic.edu.mmmtp.R;
import istic.edu.mmmtp.model.Client;

public class ClientViewActivity extends AppCompatActivity {

    // Components
    TextView txtNom;
    TextView txtNaissance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Components
        txtNom = (TextView) findViewById(R.id.txtNom);
        txtNaissance = (TextView) findViewById(R.id.txtNaissance);

        // Get form content
        Client formdata = (Client) getIntent().getParcelableExtra(EditClientActivity.EXTRA_FORM_MESSAGE);

        // Put form content in view
        String[] resultNameArgs = {formdata.getNom(),formdata.getPrenom()};
        String resultName = String.format(getResources().getString(R.string.result_name), resultNameArgs);

        String[] resultNaissanceArgs = {formdata.getVilleNaissance(),formdata.getRegionNumber(),formdata.getFormatedDateNaissance()};
        String resultNaissance = String.format(getResources().getString(R.string.result_ne), resultNaissanceArgs);

        txtNom.setText(resultName);
        txtNaissance.setText(resultNaissance);

    }

}
