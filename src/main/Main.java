package main;

import controller.Controller;
import view.LoginView;
import view.MainView;
import model.Model;
import view.NewUserView;

/**
 * Clase principal de la aplicación que configura el Modelo, la Vista y el Controlador (MVC)
 * para iniciar la aplicación de gestión de base de datos.
 * <p>
 * Este script crea instancias de cada vista y modelo, y los conecta mediante el controlador.
 * La estructura sigue el patrón MVC, con el controlador gestionando la comunicación entre
 * las vistas y el modelo.
 * </p>
 * 
 * @version 1.0
 */
public class Main { 

    /**
     * Método principal que inicia la aplicación.
     * <p>
     * Se crean las instancias de las vistas de login y principal, del modelo y del controlador,
     * que une estos componentes para hacer funcionar la aplicación.
     * </p>
     *
     *
     */
    public static void main(String[] args) {
        // Instanciación de las vistas y el modelo
        LoginView vistaLogin = new LoginView();
        MainView vistaPrincipal = new MainView();
        NewUserView vistaNuevoUsuario = new NewUserView();
        Model modelo = new Model(); 
        
        // Configuración del controlador con las vistas y el modelo
        Controller controlador = new Controller(vistaLogin, vistaPrincipal, modelo, vistaNuevoUsuario);
    }
}