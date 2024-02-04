package com.nayson.aulafirebase;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseHelper {

    private FirebaseHelper(){

    }
    public static FirebaseAuth getAuth(){
        return FirebaseAuth.getInstance();
    }

    public static boolean getUser(){
        return getAuth().getCurrentUser() != null;
    }

    public static String validError(String err){
        if(err.contains(" The email address is already in use by another account.")){
            return "Este e-mail já está registrado!";
        }else if (err.contains("The given password is invalid. [ Password should be at least 6 characters ]")){
            return "Sua senha precisa conter pelo menos 8 caracteres.";
        } else if (err.contains("The email address is badly formatted.")) {
            return "O endereço de e-mail está mal formatado.";
        } else if(err.contains("The supplied auth credential is incorrect, malformed or has expired.")) {
            return "As informações fornecidas não está relacionada à nem uma conta registrada.";
        }else if (err.contains("The email address is badly formatted.")){
            return "Esse endereço de e-mail está mal format";
        }else{
            return "Aconteceu algum erro kk";
        }
    }
}
