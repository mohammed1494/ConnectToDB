package SQLDBTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectToSQLDB {
    public static Connection connect = null;
    public static Statement statement = null;
    public static PreparedStatement ps = null;
    public static ResultSet resultSet = null;

    public static Properties loadProperties() throws IOException {
        Properties prop = new Properties();
        InputStream ism = new FileInputStream("src/secret.properties");
        prop.load(ism);
        ism.close();
        return prop;
    }

    public static Connection connectToSQLDB() throws IOException, ClassNotFoundException, SQLException {
        Properties properties = loadProperties();
        String driverClass = properties.getProperty("MYSQLJDBC.driver");
        String url = properties.getProperty("MYSQLJDBC.url");
        String userName = properties.getProperty("MYSQLJDBC.userName");
        String password = properties.getProperty("MYSQLJDBC.password");
        Class.forName(driverClass);
        connect = DriverManager.getConnection(url, userName, password);
        return connect;
        //establishing connection to local db
    }

    public static List<Student> readStudentsProfile() throws SQLException, IOException, ClassNotFoundException {
        List<Student> list = new ArrayList<>();
        Student student = null;

        try {
                Connection connection = connectToSQLDB();
                String query = "Select # from students ";
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                String stName = resultSet.getString("stName");
                String stID = resultSet.getString("stID");
                String stDOB = resultSet.getString("stDOB");

                student = new Student(stName, stID, stDOB);
                list.add(student);
            }

                } catch(IOException e){
                e.printStackTrace();
                } catch(SQLException throwables){
                throwables.printStackTrace();
                } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }

         return list;

            }


        public static void main (String[]args) throws SQLException, IOException, ClassNotFoundException {
            System.out.println("Hello database");
            List<Student> list = readStudentsProfile();
            for (Student student:list) {
                System.out.println(student.getStName() + " " + student.getStID() + " " + student.getStDOB());
            }
        }
    }
