package istic.edu.mmmtp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import istic.edu.mmmtp.R;
import istic.edu.mmmtp.adapter.ClientArrayAdapter;
import istic.edu.mmmtp.adapter.ClientCursorAdapter;
import istic.edu.mmmtp.contentprovider.ClientContentProvider;
import istic.edu.mmmtp.model.Client;

public class ClientListActivity extends AppCompatActivity {

    ArrayList<Client> clients = new ArrayList<>();

    // UI Elements
    Button btNewClient;
    ListView listViewClients;
    EditText inputSearch;

    // Adapter and data things
    ClientCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // UI Elements
        btNewClient = (Button) findViewById(R.id.bt_new_client);
        listViewClients = (ListView) findViewById(R.id.list_view_clients);
        inputSearch = (EditText) findViewById(R.id.input_search_client);

        // List View init
        setListViewAdapter();

        // Handle list click event
        listViewClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) adapter.getItem(position);
                handleListViewClientItemClick(Client.buildFromCursor(c));
            }
        });

        // Handle button click event
        btNewClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientListActivity.this.handleBtNewClientClick();
            }
        });

        // Handle search input
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                ClientListActivity.this.handleSearchInput(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {}

            @Override
            public void afterTextChanged(Editable arg0) {}
        });

    }

    private void handleBtNewClientClick() {
        // Intent to new client activity
        Intent intent = new Intent(this, EditClientActivity.class);
        startActivity(intent);
    }

    private void handleListViewClientItemClick(Client client) {
        Intent intent = new Intent(this, ClientViewActivity.class);
        intent.putExtra(EditClientActivity.EXTRA_FORM_MESSAGE, client);
        startActivity(intent);
    }

    private void setListViewAdapter() {
        final Uri clientsCpUrl = ClientContentProvider.CONTENT_URI;
        Cursor c = getContentResolver().query(clientsCpUrl, null, null, null, null);
        adapter = new ClientCursorAdapter(this, c, 0);

        adapter.setFilterQueryProvider(new FilterQueryProvider() {

            @Override
            public Cursor runQuery(CharSequence constraint) {
                return getContentResolver().query(clientsCpUrl, null, ClientContentProvider.CLIENT_NOM + " LIKE ?",
                        new String[]{"%" + constraint.toString() + "%"}, null);

            }
        });

        listViewClients.setAdapter(adapter);
    }

    private void handleSearchInput(CharSequence search) {
        ClientListActivity.this.adapter.getFilter().filter(search);
        ClientListActivity.this.adapter.notifyDataSetChanged();
    }

    private void loadClients() {
        // Disabled due to use of cursor adapter
        /*Uri clientsCpUrl = ClientContentProvider.CONTENT_URI;
        Cursor c = getContentResolver().query(clientsCpUrl, null, null, null, null);
        String s = new String();
        if (c.moveToFirst()) {
            do {

                // Format date before
                Date birthdate = null;
                try {
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                    birthdate = dateFormatter.parse(c.getString(c.getColumnIndex(
                            ClientContentProvider.CLIENT_DATENAISSANCE)));

                } catch (ParseException e) {
                    e.printStackTrace();
                    // Nothing, we just do not have birth date
                }

                Client loadedClient = Client.builder()
                        .nom(c.getString(c.getColumnIndex(
                                ClientContentProvider.CLIENT_NOM)))
                        .prenom(c.getString(c.getColumnIndex(
                                ClientContentProvider.CLIENT_PRENOM)))
                        .dateNaissance(birthdate)
                        .villeNaissance(c.getString(c.getColumnIndex(
                                ClientContentProvider.CLIENT_VILLENAISSANCE)))
                        .region(c.getString(c.getColumnIndex(
                                ClientContentProvider.CLIENT_REGION)))
                        .telephone(c.getString(c.getColumnIndex(
                                ClientContentProvider.CLIENT_TELEPHONE)))
                        .build();
                clients.add(loadedClient);

            } while (c.moveToNext());*/
    }
}
