package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Model;
import view.LoginView;
import view.MainView;
import view.NewUserView;

/**
 * Controlador principal que maneja la lógica de negocio entre las vistas
 * y el modelo. Responde a eventos de usuario y actualiza las vistas
 * de acuerdo con los datos del modelo.
 */
public class Controller {
    
    // Instancias de vistas y modelo
    private LoginView loginView; 
    private MainView mainView;
    private NewUserView newUserView;
    private Model model;
    
    /**
     * Constructor del controlador, inicializa las vistas y los listeners.
     *  
     * @param loginView Vista de inicio de sesión
     * @param mainView Vista principal de la aplicación
     * @param model Modelo de datos
     * @param newUserView Vista para crear nuevos usuarios
     */
    public Controller(LoginView loginView, MainView mainView, Model model, NewUserView newUserView) {
        this.loginView = loginView;
        this.mainView = mainView;
        this.model = model;
        this.newUserView = newUserView;
        initEventHandlers();
    }

    /**
     * Inicializa los manejadores de eventos para los botones de las vistas.
     * Muestra la vista de login inicialmente.
     */
    public void initEventHandlers() {
        loginView.setVisible(true);
        
        // Maneja el botón para abrir la vista de nuevo usuario
        mainView.btnNewUser.addActionListener(e -> newUserView.setVisible(true));
        
        // Maneja el botón de logout
        mainView.btnLogout.addActionListener(e -> handleLogout());

        // Maneja el botón de login en la vista de inicio de sesión
        loginView.btnLogin.addActionListener(e -> handleLogin());

        // Maneja el botón para crear un nuevo usuario en la vista de registro
        newUserView.btnCreateUser.addActionListener(e -> handleUserCreation());

        // Maneja el botón para importar datos desde la base de datos
        mainView.btnImportDB.addActionListener(e -> model.importDb(mainView.textArea));

        // Maneja el botón para buscar un usuario específico en la base de datos
        mainView.btnSearchUser.addActionListener(e -> handleUserSearch());

        // Maneja el botón para guardar la tabla actual como archivo CSV
        mainView.btnSaveAsCvs.addActionListener(e -> handleSaveAsCSV());

        // Maneja el botón para enviar consultas a la base de datos
        mainView.btnSendQ.addActionListener(e -> handleSendQuery());
    }

    /**
     * Realiza la acción de login, verificando credenciales y mostrando la vista principal si son válidas.
     */
    private void handleLogin() {
        String userName = loginView.userName.getText();
        char[] password = loginView.password.getPassword();
        
        if (model.validateUser(userName, new String(password))) {
            mainView.setVisible(true);
            loginView.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Login incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Realiza la acción de logout, cerrando la vista principal y mostrando nuevamente la vista de login.
     */
    private void handleLogout() {
        model.logout();
        mainView.setVisible(false);
        loginView.setVisible(true);
    }

    /**
     * Maneja la creación de un nuevo usuario, validando los datos ingresados y mostrando un mensaje de éxito o error.
     */
    private void handleUserCreation() {
        String userName = newUserView.newUserName.getText();
        char[] password = newUserView.newPassword.getPassword();
        char[] confirmedPassword = newUserView.confirmPassword.getPassword();

        if (model.createUser(userName, new String(password), new String(confirmedPassword))) {
            JOptionPane.showMessageDialog(null, "Usuario creado", "Completado", JOptionPane.INFORMATION_MESSAGE);
            newUserView.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error al crear usuario", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Maneja la búsqueda de un usuario, mostrando los resultados en la tabla si el usuario tiene permiso.
     */
    private void handleUserSearch() {
        if (!model.searchUser(mainView.usernamesearch.getText(), mainView.table)) {
            JOptionPane.showMessageDialog(null, "No tienes permiso", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Guarda la tabla actual como archivo CSV, mostrando un mensaje de confirmación o error.
     */
    private void handleSaveAsCSV() {
        if (model.writeCVSFile(mainView.table, mainView.tablename.getText())) {
            JOptionPane.showMessageDialog(null, "Archivo CSV creado", "Completado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Error al crear archivo CSV", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Envía una consulta a la base de datos basada en los filtros y opciones seleccionados.
     */
    private void handleSendQuery() {
        // Obtener selección de campos a mostrar
        boolean showAge = mainView.chckbxAge.isSelected();
        boolean showPopulation = mainView.chckbxPopulation.isSelected();
        boolean showDensity = mainView.chckbxDensity.isSelected();
        boolean showArea = mainView.chckbxArea.isSelected();
        boolean showFertility = mainView.chckbxFertility.isSelected();
        boolean showUrban = mainView.chckbxUrban.isSelected();
        boolean showShare = mainView.chckbxShare.isSelected();

        // Obtener filtros de búsqueda
        String name = mainView.name.getText();
        String density = mainView.density.getText();
        String modDensity = mainView.modDensity.getSelectedItem().toString();
        String population = mainView.population.getText();
        String modPopulation = mainView.modPopulation.getSelectedItem().toString();
        String area = mainView.area.getText();
        String modArea = mainView.modArea.getSelectedItem().toString();
        String fertility = mainView.fertility.getText();
        String modFertility = mainView.modFertility.getSelectedItem().toString();
        String age = mainView.age.getText();
        String modAge = mainView.modAge.getSelectedItem().toString();
        String urban = mainView.urban.getText();
        String modUrban = mainView.modUrban.getSelectedItem().toString();
        String share = mainView.share.getText();
        String modShare = mainView.modShare.getSelectedItem().toString();

        // Ejecutar consulta en el modelo
        model.sendQuery(showPopulation, showDensity, showArea, showFertility, showAge, showUrban, showShare, 
                        name, density, modDensity, population, modPopulation, area, modArea, fertility, modFertility, 
                        age, modAge, urban, modUrban, share, modShare, mainView.table);
    }
}
