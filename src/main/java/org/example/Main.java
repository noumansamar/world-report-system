package org.example;

import org.example.db.DatabaseConnection;
import java.sql.Connection;
public class Main
{
    public static void main(String[] args)
    {
        DatabaseConnection db = new DatabaseConnection();
        try (Connection conn = db.getConnection())
        {
            System.out.println("Connected: " + !conn.isClosed());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            db.disconnect();
        }
    }
}