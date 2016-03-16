package istic.edu.mmmtp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import istic.edu.mmmtp.R;
import istic.edu.mmmtp.model.Client;

/**
 * Created by maxime on 12/02/2016.
 */
public class ClientArrayAdapter extends ArrayAdapter<Client> {
    private Activity activity;
    private ArrayList<Client> clients;
    private static LayoutInflater inflater = null;



    public ClientArrayAdapter(Activity activity, ArrayList<Client> clients) {
        super(activity, 0, clients);
        try {
            this.activity = activity;
            this.clients = clients;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return clients.size();
    }

    public Client getItem(Client position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView client_name;
        public TextView client_birthdate;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.list_view_clientlist_row, null);
                holder = new ViewHolder();

                holder.client_name = (TextView) vi.findViewById(R.id.clientlist_row_client_name);
                holder.client_birthdate = (TextView) vi.findViewById(R.id.clientlist_row_client_info);
                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            String clientName = clients.get(position).getNom() + " " + clients.get(position).getPrenom();
            holder.client_name.setText(clientName);
            holder.client_birthdate.setText(clients.get(position).getFormatedDateNaissance());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return vi;
    }
}