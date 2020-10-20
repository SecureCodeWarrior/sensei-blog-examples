import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tododb.DbApi;
import tododb.MyDB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyFirstDBTest {

    static DbApi myDB;

    @BeforeAll
    static void setupDB(){
        myDB = new DbApi(new MyDB().create());
    }

    @Test
    void myFirstSelect() throws SQLException {

        String doneStatus = "1";
        ResultSet rs = myDB.getTodosOfStatus(doneStatus);

        Assertions.assertTrue(rs.next());
        Assertions.assertEquals("I did that activity", rs.getString("description"));

    }

    @Test
    void myFirstSQLInjection() throws SQLException {

        // get the admin user instead
        String doneStatus = "-1 UNION SELECT name || '~' || password as description from users";

        // TODO: fix the SQL Injection issue in the DBApi
        ResultSet rs = myDB.getTodosOfStatus(doneStatus);

        Assertions.assertTrue(rs.next());
        Assertions.assertEquals("admin~root", rs.getString("description"));

    }
}
