package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class md5Hash {
    public static String convertirAHashMD5(String texto) {
        try {
            // Crea una instancia de MessageDigest con el algoritmo MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            // Convierte el texto en un array de bytes
            byte[] bytesDelTexto = texto.getBytes();
            
            // Calcula el hash MD5
            byte[] hashBytes = md.digest(bytesDelTexto);
            
            // Convierte el array de bytes a una representación hexadecimal 
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            
            // Devuelve el hash en formato hexadecimal
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: Algoritmo MD5 no disponible", e);
        }
    }

    public static void main(String[] args) {
        String texto = "admin";
        String hashMD5 = convertirAHashMD5(texto);
        System.out.println("Hash MD5 de 'admin': " + hashMD5);
    }
}
