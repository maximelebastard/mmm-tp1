package istic.edu.mmmtp.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import istic.edu.mmmtp.contentprovider.ClientContentProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by maxime on 10/02/2016.
 */
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
@ToString
@Builder
public class Client implements Parcelable {
    @Getter @Setter
    protected String nom;

    @Getter @Setter
    protected String prenom;

    @Getter @Setter
    protected Date dateNaissance;

    @Getter @Setter
    protected String villeNaissance;

    @Getter @Setter
    protected String telephone;

    @Getter @Setter
    protected String region;

    public String getRegionNumber() {
        String[] regionSplit = region.split(":");

        return regionSplit[0];
    }

    public String getFormatedDateNaissance() {
        DateFormat formatter = new SimpleDateFormat();
        return formatter.format(dateNaissance);
    }

    // Parcelable management
    protected Client(Parcel in) {
        nom = in.readString();
        prenom = in.readString();
        dateNaissance = (Date) in.readSerializable();
        villeNaissance = in.readString();
        region = in.readString();
        telephone = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nom);
        dest.writeString(prenom);
        dest.writeSerializable(dateNaissance);
        dest.writeString(villeNaissance);
        dest.writeString(region);
        dest.writeString(telephone);
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    public static Client buildFromCursor(Cursor c) {
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

        return loadedClient;
    }
}
