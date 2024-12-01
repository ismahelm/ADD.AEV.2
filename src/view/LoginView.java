package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;

/**
 * Vista de la interfaz de inicio de sesión (login) que permite al usuario ingresar
 * su nombre de usuario y contraseña para acceder a la aplicación.
 * <p>
 * Esta clase extiende {@link JFrame} y proporciona campos de texto para
 * nombre de usuario y contraseña, así como un botón de inicio de sesión.
 * </p>
 *
 * @version 1.0
 */
public class LoginView extends JFrame {
	
    private static final long serialVersionUID = 1L;

    /** Panel principal de contenido de la vista */
    private JPanel contentPane;

    /** Botón para iniciar sesión */
    public JButton btnLogin;

    /** Campo de texto para ingresar el nombre de usuario */
    public JTextField userName;

    /** Campo de texto para ingresar la contraseña */
    public JPasswordField password;

    /**
     * Constructor de la clase LoginView.
     * <p>
     * Configura la ventana principal, los campos de entrada y el botón de inicio de sesión.
     * </p>
     */
    public LoginView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setBounds(100, 100, 450, 300);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        btnLogin = new JButton("login");
        btnLogin.setBounds(185, 209, 80, 23);
        contentPane.add(btnLogin);

        userName = new JTextField();
        userName.setBounds(165, 76, 120, 20);
        contentPane.add(userName);
        userName.setColumns(10);

        password = new JPasswordField();
        password.setBounds(165, 138, 120, 23);
        contentPane.add(password);
    }

    /**
     * Asigna un `ActionListener` al botón de inicio de sesión.
     * 
     * @param listener El ActionListener que gestionará el evento de clic en el botón de login.
     */
    public void addLoginButtonListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }
}
