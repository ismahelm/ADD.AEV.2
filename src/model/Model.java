package model;

import controller.md5Hash;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.sql.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

/**
 * Clase que maneja la lógica de interacción con la base de datos y la manipulación de archivos
 * como CSV y XML, así como la gestión de usuarios y autenticación.
 */
public class Model {
    static String loggedUser;
    static String currentPassword;

    /**
     * Realiza la búsqueda de un usuario por nombre y muestra los resultados en una tabla.
     * 
     * @param name  El nombre del usuario a buscar.
     * @param table La tabla donde se mostrarán los resultados.
     * @return true si el usuario fue encontrado, false en caso contrario.
     */
    public static boolean searchUser(String name, JTable table) {
        boolean userFound = false;
        try {
            StringBuilder query = new StringBuilder("SELECT * FROM users WHERE login = ?");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", loggedUser, currentPassword);
            PreparedStatement pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            String[] columnNames = new String[metaData.getColumnCount()];
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(columnNames);

            while (rs.next()) {
                Object[] rowData = new Object[metaData.getColumnCount()];
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    rowData[i - 1] = rs.getObject(i);
                } 
                model.addRow(rowData);
            }

            table.setModel(model);
            userFound = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userFound;
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    public static void logout() {
        loggedUser = "";
        currentPassword = "";
    }

    /**
     * Valida las credenciales de un usuario.
     * 
     * @param userName El nombre de usuario.
     * @param password La contraseña del usuario.
     * @return true si el usuario y la contraseña son válidos, false en caso contrario.
     */
    public static boolean validateUser(String userName, String password) {
        boolean userValidated = false;
        password = md5Hash.convertirAHashMD5(password);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", userName, password);
            userValidated = true;
            loggedUser = userName;
            currentPassword = password;
            con.close();
        } catch (Exception e) {
            userValidated = false;
        }

        return userValidated;
    }

    /**
     * Crea un nuevo usuario en la base de datos.
     * 
     * @param userName        El nombre del usuario.
     * @param password        La contraseña del usuario.
     * @param confirmedPassword La confirmación de la contraseña.
     * @return true si el usuario fue creado correctamente, false en caso contrario.
     */
    public static boolean createUser(String userName, String password, String confirmedPassword) {
        boolean userCreated = false;
        if (!password.equals(confirmedPassword)) {
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                password = md5Hash.convertirAHashMD5(password);
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", loggedUser, currentPassword);

                PreparedStatement psInsertUser = con.prepareStatement("INSERT INTO users (id, login, password, type) VALUES (?, ?, ?, ?)");
                psInsertUser.setString(1, null);
                psInsertUser.setString(2, userName);
                psInsertUser.setString(3, password);
                psInsertUser.setString(4, "client");

                int result = psInsertUser.executeUpdate();

                if (result > 0) {
                    Statement stmt = con.createStatement();
                    String createUserSQL = "CREATE USER '" + userName + "' IDENTIFIED BY '" + password + "';";
                    String grantPermissionsSQL = "GRANT SELECT ON population.population TO '" + userName + "';";
                    stmt.executeUpdate(createUserSQL);
                    stmt.executeUpdate(grantPermissionsSQL);
                    stmt.close();
                }
                psInsertUser.close();
                con.close();
                userCreated = true;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return userCreated;
    }

    /**
     * Escribe un archivo XML con los datos proporcionados.
     * 
     * @param fields Los nombres de los campos.
     * @param data   Los valores correspondientes a los campos.
     */
    public static void writeXmlFile(String[] fields, String[] data) {
        try {
            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();

            Element root = doc.createElement("Country");
            doc.appendChild(root);

            for (int i = 0; i < data.length; i++) {
                Element newElement = doc.createElement(fields[i]);
                newElement.appendChild(doc.createTextNode(data[i]));
                root.appendChild(newElement);
            }

            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer transformer = tranFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            FileWriter fw = new FileWriter("./src/xmls/" + data[0] + ".xml");
            StreamResult result = new StreamResult(fw);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException | IOException ex) {
            System.out.println("Error construyendo el documento");
        }
    }

    /**
     * Lee todos los archivos XML en el directorio y muestra su contenido en un JTextArea.
     * 
     * @param fields Los nombres de los campos.
     * @param console El área de texto donde se mostrará el contenido de los archivos.
     */
    public static void readDirectory(String[] fields, JTextArea console) {
        StringBuilder result = new StringBuilder();
        try {
            File dir = new File("./src/xmls");
            String[] fileList = dir.list();

            for (String fileName : fileList) {
                String line;
                File file = new File(dir, fileName);
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                while ((line = br.readLine()) != null) {
                    result.append(line).append("\n");
                }
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        console.setText(result.toString());
    }

    /**
     * Importa datos desde un archivo CSV a la base de datos y genera archivos XML.
     * 
     * @param console El área de texto donde se mostrará el proceso de importación.
     */
    public static void importDb (JTextArea console)
	{
		
		try {
			File importeddb = new File ("./AE02_population.csv");
			FileReader fr = new FileReader(importeddb);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();	          
			System.out.println(line);
			String[] fields = line.split(";"); 
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", loggedUser, currentPassword);
	        Statement stmt = con.createStatement();
	        
	        String dropTableSQL = "DROP TABLE IF EXISTS population;";
            stmt.executeUpdate(dropTableSQL);
            
            StringBuilder createTableSQL = new StringBuilder("CREATE TABLE population (");
            for (int i  =0; i<fields.length;i++)
            {
            	createTableSQL.append(fields[i]).append(" VARCHAR(30)");
            	if (i<fields.length-1)
            	{
            		createTableSQL.append(", ");
            	}
            }
            createTableSQL.append(");");
            stmt.executeUpdate(createTableSQL.toString());
            
            while ((line=br.readLine())!=null)
            {
            	String[] data = line.split(";");
            	writeXmlFile(fields, data);
            	readDirectory(fields, console);
            }
            
            // Crear el constructor de documentos
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    
		    File dir = new File ("./src/xmls");
		    String[] fileList = dir.list();
		    
		    
            StringBuilder importData = new StringBuilder("INSERT INTO population (");
            for (int i  =0; i<fields.length;i++)
            {
            	importData.append(fields[i]);
            	if (i<fields.length-1)
            	{
            		importData.append(", ");
            	}            		
            }
            importData.append(") VALUES (");
            for (int i  =0; i<fields.length;i++)
            {
            	importData.append("?");
            	if (i<fields.length-1)
            	{
            		importData.append(", ");
            	}            		
            }
            importData.append(");");
            System.out.println(importData.toString());
		    
		    //cada archivo
		    for (int i = 0; i < fileList.length; i++) 
		    {
		    	PreparedStatement importcountries = con.prepareStatement(importData.toString());
	            File file = new File(dir, fileList[i]);
	            Document document = dBuilder.parse(file);	
	            Element raiz = document.getDocumentElement();
	            //cada campo
	            for (int z = 0;z<fields.length;z++)
	            {
	            	NodeList nodeList = raiz.getElementsByTagName(fields[z]);
	            	Node node = nodeList.item(0);
	            	if (node.getNodeType() == Node.ELEMENT_NODE) {
	                    Element eElement = (Element) node;
	                    String data = node.getTextContent();
	                    importcountries.setString(z+1, data);
	            	}
	            	
	            }
	           // System.out.println(importcountries.toString());
	            int resultadoInsertar = importcountries.executeUpdate();
		    }
		           
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    /**
     * Guarda los datos de una tabla en un archivo CSV.
     * 
     * @param table La tabla cuyo contenido será guardado.
     * @param name El nombre que se dará al archivo CSV.
     * @return true si el archivo se guardó correctamente, false en caso contrario.
     */
    public static boolean writeCVSFile(JTable table, String name) {
        boolean savedFile = false;
        try {
            // Obtener el modelo de la tabla
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            File saveFile = new File("./src/tables/" + name + ".csv");
            FileWriter fw = new FileWriter(saveFile);
            BufferedWriter bw = new BufferedWriter(fw);
            
            // Escribir los nombres de las columnas
            String[] columnNames = new String[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                bw.write(model.getColumnName(i));
                if (i < model.getColumnCount() - 1) {
                    bw.write(";");
                }
            }
            bw.newLine();
            
            // Recorrer las filas de la tabla y escribir sus valores
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    String value = model.getValueAt(i, j).toString();
                    bw.write(value);
                    if (j < model.getColumnCount() - 1) {
                        bw.write(";");
                    }
                }
                bw.newLine();
            }
            
            // Cerrar los streams
            bw.close();
            fw.close();
            savedFile = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savedFile;
    }

    /**
     * Envía una consulta SQL a la base de datos y muestra los resultados en una JTable.
     * 
     * @param showPopulation Si se debe mostrar la población en la consulta.
     * @param showDensity Si se debe mostrar la densidad en la consulta.
     * @param showArea Si se debe mostrar el área en la consulta.
     * @param showFertility Si se debe mostrar la fertilidad en la consulta.
     * @param showAge Si se debe mostrar la edad en la consulta.
     * @param showUrban Si se debe mostrar la urbanización en la consulta.
     * @param showShare Si se debe mostrar la participación en la consulta.
     * @param name Nombre del país para filtrar la consulta.
     * @param density Valor de densidad para filtrar.
     * @param modDensity Modificador para filtrar la densidad.
     * @param population Valor de población para filtrar.
     * @param modPopulation Modificador para filtrar la población.
     * @param area Valor de área para filtrar.
     * @param modArea Modificador para filtrar el área.
     * @param fertility Valor de fertilidad para filtrar.
     * @param modFertility Modificador para filtrar la fertilidad.
     * @param age Valor de edad para filtrar.
     * @param modAge Modificador para filtrar la edad.
     * @param urban Valor de urbanización para filtrar.
     * @param modUrban Modificador para filtrar la urbanización.
     * @param share Valor de participación para filtrar.
     * @param modShare Modificador para filtrar la participación.
     * @param table La tabla en la que se mostrarán los resultados.
     */
    public static void sendQuery(
            boolean showPopulation, boolean showDensity, boolean showArea, boolean showFertility,
            boolean showAge, boolean showUrban, boolean showShare,
            String name, String density, String modDensity, String population, String modPopulation,
            String area, String modArea, String fertility, String modFertility,
            String age, String modAge, String urban, String modUrban,
            String share, String modShare, JTable table) {

        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", loggedUser, currentPassword);
            
            // Construcción de la consulta SQL dinámicamente según los parámetros recibidos
            StringBuilder query = new StringBuilder("SELECT country");
            query.append(showPopulation ? ", population" : "");
            query.append(showDensity ? ", density" : "");
            query.append(showArea ? ", area" : "");
            query.append(showFertility ? ", fertility" : "");
            query.append(showAge ? ", age" : "");
            query.append(showUrban ? ", urban" : "");
            query.append(showShare ? ", share" : "");
            query.append(" FROM population WHERE ");
            
            // Agregar filtros a la consulta según los parámetros proporcionados
            if (!name.isEmpty()) {
                query.append("country = ? AND ");
            }
            if (!population.isEmpty()) {
                query.append("CAST(population AS INT) " + modPopulation + " ? AND ");
            }
            if (!density.isEmpty()) {
                query.append("CAST(density AS INT) " + modDensity + " ? AND ");
            }
            if (!area.isEmpty()) {
                query.append("CAST(area AS INT) " + modArea + " ? AND ");
            }
            if (!fertility.isEmpty()) {
                query.append("CAST(REPLACE(fertility, ',', '.') AS DOUBLE) " + modFertility + " ? AND ");
            }
            if (!age.isEmpty()) {
                query.append("CAST(age AS INT) " + modAge + " ? AND ");
            }
            if (!urban.isEmpty()) {
                query.append("CAST(REPLACE(urban, '%', '') AS INT) " + modUrban + " ? AND ");
            }
            if (!share.isEmpty()) {
                query.append("CAST(share AS FLOAT) " + modShare + " ? AND ");
            }
            
            query.append("1");  // Fin de la cláusula WHERE
            
            // Preparar y ejecutar la consulta
            PreparedStatement pstmt = con.prepareStatement(query.toString());
            int paramIndex = 1;

            if (!name.isEmpty()) pstmt.setString(paramIndex++, name);
            if (!population.isEmpty()) pstmt.setString(paramIndex++, population);
            if (!density.isEmpty()) pstmt.setString(paramIndex++, density);
            if (!area.isEmpty()) pstmt.setString(paramIndex++, area);
            if (!fertility.isEmpty()) pstmt.setString(paramIndex++, fertility);
            if (!age.isEmpty()) pstmt.setString(paramIndex++, age);
            if (!urban.isEmpty()) pstmt.setString(paramIndex++, urban);
            if (!share.isEmpty()) pstmt.setString(paramIndex++, share);

            System.out.println(pstmt.toString());
            ResultSet rs = pstmt.executeQuery();
            
            // Obtener metadatos de la consulta para configurar la tabla
            ResultSetMetaData metaData = rs.getMetaData();
            String[] columnNames = new String[metaData.getColumnCount()];
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            
            // Configurar el modelo de la tabla con los resultados obtenidos
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(columnNames);
            
            // Agregar las filas a la tabla
            while (rs.next()) {
                Object[] rowData = new Object[metaData.getColumnCount()];
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                model.addRow(rowData);
            }
            // Asignar el modelo a la tabla
            table.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
