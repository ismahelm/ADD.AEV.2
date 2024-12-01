package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;

/**
 * La clase NewUserView representa la interfaz gráfica para la creación de un nuevo usuario.
 * Hereda de JFrame y proporciona campos de entrada para el nombre de usuario, contraseña, 
 * confirmación de contraseña, y un botón para confirmar la creación del usuario.
 */
public class NewUserView extends JFrame {

    // Serialización para la clase JFrame.
    private static final long serialVersionUID = 1L;

    // Componentes principales de la ventana
    public JPanel contentPane;             // Panel principal de la ventana.
    public JTextField newUserName;         // Campo de texto para ingresar el nombre de usuario.
    public JPasswordField newPassword;     // Campo de contraseña para ingresar la clave.
    public JPasswordField confirmPassword; // Campo de contraseña para confirmar la clave.
    public JButton btnCreateUser;          // Botón para crear el usuario.

    // Etiquetas informativas para guiar al usuario
    public JTextArea txtrUsername;        // Etiqueta de "username" (nombre de usuario).
    public JTextArea txtrPassword;        // Etiqueta de "password" (contraseña).
    public JTextArea txtrPasssword;       // Etiqueta de "repeat password" (repetir contraseña).

    /**
     * Método principal para ejecutar la ventana de NewUserView.
     * @param args Argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    NewUserView frame = new NewUserView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Constructor de NewUserView. Configura el marco de la ventana, 
     * el panel de contenido y los componentes gráficos.
     */
    public NewUserView() {
        // Configuración del cierre y dimensiones de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 276, 300);

        // Inicialización del panel de contenido con un fondo blanco y borde
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // Configuración del campo de texto para el nombre de usuario
        newUserName = new JTextField();
        newUserName.setBounds(29, 55, 195, 20);
        contentPane.add(newUserName);
        newUserName.setColumns(10);
        
        // Configuración del campo de contraseña para la nueva contraseña
        newPassword = new JPasswordField();
        newPassword.setBounds(29, 182, 195, 20);
        contentPane.add(newPassword);
        newPassword.setColumns(10);
        
        // Configuración del campo de contraseña para confirmar la nueva contraseña
        confirmPassword = new JPasswordField();
        confirmPassword.setBounds(29, 119, 195, 20);
        contentPane.add(confirmPassword);
        confirmPassword.setColumns(10);
        
        // Configuración del botón para crear usuario
        btnCreateUser = new JButton("Create User");
        btnCreateUser.setBounds(161, 227, 89, 23);
        contentPane.add(btnCreateUser);
        
        // Etiqueta de texto para el campo de nombre de usuario
        txtrUsername = new JTextArea();
        txtrUsername.setText("username");
        txtrUsername.setBounds(81, 23, 86, 22);
        contentPane.add(txtrUsername);
        
        // Etiqueta de texto para el campo de contraseña
        txtrPassword = new JTextArea();
        txtrPassword.setText("password");
        txtrPassword.setBounds(81, 91, 86, 22);
        contentPane.add(txtrPassword);
        
        // Etiqueta de texto para el campo de confirmación de contraseña
        txtrPasssword = new JTextArea();
        txtrPasssword.setText("repeat password");
        txtrPasssword.setBounds(52, 151, 146, 20);
        contentPane.add(txtrPasssword);
    }
}
