package io.github.mrixs.blockplacinglimiter;

import java.sql.*;

/**
 * Created by Mrixs on 04.Feb.2015 17:32
 * Project: BlockPlacingLimiter
 * Package: io.github.mrixs.blockplacinglimiter
 */


public class DB {
    public static Connection conn;


    public static void Conn() throws ClassNotFoundException, SQLException {

        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:BPL.s3db");
        //log.info("BPL: Connected to database");

    }


    public static void CreateDB() throws ClassNotFoundException, SQLException {

        Statement statmt;
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'placedLimitedBlocks' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'type' INTEGER , 'x' INTEGER, 'y' INTEGER, 'z' INTEGER, 'dimension' INTEGER);");
        //log.info("BPL: Database (stores already placed blocks) created");
        statmt.close();
    }


    public static void AddBlock(int x, int y, int z, int dimension, int blockid) throws SQLException {

        Statement statmt = conn.createStatement();
        statmt = conn.createStatement();
        statmt.execute("INSERT INTO 'placedLimitedBlocks' ('type', 'x', 'y' 'z' 'dimension') VALUES (" + blockid + "," + x + "," + y + "," + z + "," + dimension + "; ");
        //log.info("BPL: Placed block (" + x + "," + y + "," + z + ")");
        statmt.close();

    }


    public static int CountPlaced(int type) throws ClassNotFoundException, SQLException {

        ResultSet resSet;
        Statement statmt = conn.createStatement();
        resSet = statmt.executeQuery("SELECT COUNT(*) FROM 'placedLimitedBlocks' WHERE 'type'=" + type + ";");
        statmt.close();
        resSet.close();
        return resSet.getInt("id");
    }


    public static void CloseDB() throws ClassNotFoundException, SQLException {
        conn.close();
        // log.info("BPL: Disconnected from database");
    }

}
