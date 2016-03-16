package istic.edu.mmmtp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import istic.edu.mmmtp.R;
import istic.edu.mmmtp.model.Client;

/**
 * Created by maxime on 16/03/2016.
 */
public class ClientCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    public ClientCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(R.layout.list_view_clientlist_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView clientName = (TextView) view.findViewById(R.id.clientlist_row_client_name);
        TextView clientInfo = (TextView) view.findViewById(R.id.clientlist_row_client_info);

        Client client = Client.buildFromCursor(cursor);

        String clientNameText = client.getNom() + " " + client.getPrenom();
        String clientInfoText = client.getFormatedDateNaissance() + " - " + client.getVilleNaissance();

        clientName.setText(clientNameText);
        clientInfo.setText(clientInfoText);
    }
}
