package istic.edu.mmtp1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dd.processbutton.FlatButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import istic.edu.mmtp1.formdata.SignInFormData;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // Components
    private RelativeLayout mainLayout;
    private EditText iptNom;
    private EditText iptPrenom;
    private EditText iptDateNaissance;
    private EditText iptTelephone;
    private AutoCompleteTextView iptVilleNaissance;
    private FlatButton btValider;

    private final String DATEPICKER_DATENAISSANCE_TAG = "DATEPICKER_DATENAISSANCE_TAG";

    private boolean phoneEnabled = false;

    String[] mainFrenchCities={"Paris","Marseille","Lyon","Toulouse","Rennes"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Components
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        iptNom = (EditText) findViewById(R.id.iptNom);
        iptPrenom = (EditText) findViewById(R.id.iptPrenom);
        iptDateNaissance = (EditText) findViewById(R.id.iptDateNaissance);
        iptVilleNaissance = (AutoCompleteTextView) findViewById(R.id.iptVilleNaissance);
        btValider = (FlatButton) findViewById(R.id.btValider);

        // Handle datepicker for dates
        iptDateNaissance.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Datepicker
                    Calendar defaultDate = Calendar.getInstance();
                    defaultDate.add(Calendar.YEAR, -20);
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            MainActivity.this,
                            defaultDate.get(Calendar.YEAR),
                            defaultDate.get(Calendar.MONTH),
                            defaultDate.get(Calendar.DAY_OF_MONTH)
                    );
                    dpd.show(getFragmentManager(), DATEPICKER_DATENAISSANCE_TAG);
                }
            }
        });

        // Handle autocomplete for villeNaissance
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mainFrenchCities);
        iptVilleNaissance.setAdapter(adapter);
        iptVilleNaissance.setThreshold(1);

        // Handle validate form
        btValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processForm();
            }
        });

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear_form) {
            clearForm();
        }

        else if (id == R.id.action_enable_phone) {
            phoneEnabled = !phoneEnabled;
            item.setChecked(phoneEnabled);

            refreshPhoneDisplay();
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshPhoneDisplay() {
        if(phoneEnabled) {

            // Create input
            iptTelephone = new EditText(this);
            iptTelephone.setHint("Téléphone...");

            // Add input to view
            mainLayout.addView(iptTelephone);
            iptTelephone.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iptTelephone.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.iptVilleNaissance);
        }
        else
        {
            ((ViewManager) iptTelephone.getParent()).removeView(iptTelephone);
            iptTelephone = null;
        }
    }

    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        // Get the date and convert it
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, monthOfYear);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        DateFormat dateFormat = SimpleDateFormat.getDateInstance();
        String stringLocaleDate = dateFormat.format(date.getTime());

        // Manage datepicker results
        // Date naissance
        if(view.getTag().equals(DATEPICKER_DATENAISSANCE_TAG)) {
            iptDateNaissance.setText(stringLocaleDate);
            iptDateNaissance.clearFocus();
        }
    }

    private void processForm() {
        SignInFormData formdata = hydrateSignInForm();
        Toast.makeText(MainActivity.this, formdata.toString(), Toast.LENGTH_LONG).show();
    }

    private SignInFormData hydrateSignInForm() {
        // Date naissance parsing
        DateFormat dateFormat = SimpleDateFormat.getDateInstance();
        Date dateNaissance = null;
        try {
            dateNaissance = dateFormat.parse(iptDateNaissance.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        iptDateNaissance.getText().toString();

        SignInFormData formdata = SignInFormData.builder()
                .nom(iptNom.getText().toString())
                .prenom(iptPrenom.getText().toString())
                .dateNaissance(dateNaissance)
                .villeNaissance(iptVilleNaissance.getText().toString())
                .build();

        if(iptTelephone != null) {
            formdata.setTelephone(iptTelephone.getText().toString());
        }

        return formdata;
    }

    private void clearForm() {
        for (int i = 0, count = mainLayout.getChildCount(); i < count; ++i) {
            View view = mainLayout.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
        }
    }

}
