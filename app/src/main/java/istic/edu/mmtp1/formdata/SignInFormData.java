package istic.edu.mmtp1.formdata;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by maxime on 10/02/2016.
 */
@Builder
@ToString
public class SignInFormData {
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
}
