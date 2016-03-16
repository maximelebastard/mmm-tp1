package istic.edu.mmmtp.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.sql.Date;

import istic.edu.mmmtp.model.Client;

/**
 * Created by maxime on 16/03/2016.
 */
public class ClientContentProvider extends ContentProvider {

    public static Client data[];

    public static final String _ID = "_ID";
    public static final String CLIENT_NOM = "nom";
    public static final String CLIENT_PRENOM = "prenom";
    public static final String CLIENT_DATENAISSANCE = "dateNaissance";
    public static final String CLIENT_VILLENAISSANCE = "villeNaissance";
    public static final String CLIENT_TELEPHONE = "telephone";
    public static final String CLIENT_REGION = "region";

    // This must be the same as what as specified as the Content Provider authority
    // in the manifest file.
    static final String AUTHORITY = "clientcontentprovider";

    public static final String PROVIDER_NAME = "clientcontentprovider";

    public static final Uri CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME);

    @Override
    public boolean onCreate() {
        // Init data
        data = new Client[3];
        data[0] = Client.builder()
                .nom("HEMINGWAY")
                .prenom("Ernest")
                .dateNaissance(Date.valueOf("1950-01-01"))
                .villeNaissance("Bilbao")
                .region("35")
                .telephone("0299112233")
                .build();
        data[1] = Client.builder()
                .nom("HUGO")
                .prenom("Victor")
                .dateNaissance(Date.valueOf("1810-03-09"))
                .villeNaissance("Paris")
                .region("75")
                .telephone("0100112233")
                .build();
        data[2] = Client.builder()
                .nom("FREUD")
                .prenom("Sigmund")
                .dateNaissance(Date.valueOf("1856-06-17"))
                .villeNaissance("Brême")
                .region("17")
                .telephone("0517112233")
                .build();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        MatrixCursor c = new MatrixCursor(new String[] {
                _ID,
                CLIENT_NOM,
                CLIENT_PRENOM,
                CLIENT_DATENAISSANCE,
                CLIENT_VILLENAISSANCE,
                CLIENT_REGION,
                CLIENT_TELEPHONE
        });

        int row_index = 0;

        // Add x-axis data
        for (int i=0; i< data.length; i++) {


            c.newRow()
                    .add(row_index)
                    .add(data[row_index].getNom())
                    .add(data[row_index].getPrenom())   // Only create data for the first series.
                    .add(data[row_index].getDateNaissance())
                    .add(data[row_index].getVilleNaissance())
                    .add(data[row_index].getRegion())
                    .add(data[row_index].getTelephone());

            row_index++;
        }

        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return ContentResolver.CURSOR_DIR_BASE_TYPE + '/' + "com.clients";
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException(" To be implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException(" To be implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException(" To be implemented");
    }
}
