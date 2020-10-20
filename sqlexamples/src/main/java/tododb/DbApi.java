package tododb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbApi {

    private final Connection dbConnection;
    Logger logger = Logger.getLogger(DbApi.class.getName());

    public DbApi(Connection connection) {
        this.dbConnection = connection;
    }

    public ResultSet getTodosOfStatus(String status) {
        try {
            Statement stm = dbConnection.createStatement();
            return stm.executeQuery("SELECT description from todos where status=" + status);

        }catch(Exception e){
            logger.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }
}
