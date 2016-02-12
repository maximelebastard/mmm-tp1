package istic.edu.mmtp1.formdata;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by maxime on 10/02/2016.
 */
@NoArgsConstructor
@ToString
public class SignInFormData implements Parcelable {
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

    protected SignInFormData(Parcel in) {
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

    public static final Creator<SignInFormData> CREATOR = new Creator<SignInFormData>() {
        @Override
        public SignInFormData createFromParcel(Parcel in) {
            return new SignInFormData(in);
        }

        @Override
        public SignInFormData[] newArray(int size) {
            return new SignInFormData[size];
        }
    };

    public String getRegionNumber() {
        String[] regionSplit = region.split(":");

        return regionSplit[0];
    }

    public String getFormatedDateNaissance() {
        DateFormat formatter = new SimpleDateFormat();
        return formatter.format(dateNaissance);
    }
}
