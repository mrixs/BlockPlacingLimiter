package io.github.mrixs.blockplacinglimiter;

import java.sql.*;

/**
 * Created by Mrixs on 04.Feb.2015 17:32
 * Project: BlockPlacingLimiter
 * Package: io.github.mrixs.blockplacinglimiter
 */


class DB {
    private static Connection conn;

    public static void Conn() throws ClassNotFoundException, SQLException {

        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:BPL.s3db");
        System.out.println("BPL: Connected to database");
    }

    public static void CreateDB() throws SQLException {
        Statement statmt;
        String request="CREATE TABLE if not exists 'placedLimitedBlocks' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'type' INTEGER , 'x' INTEGER, 'y' INTEGER, 'z' INTEGER, 'dimension' INTEGER, 'player' TEXT );";
        statmt = conn.createStatement();
        statmt.execute(request);
        System.out.println("BPL: Database (stores already placed blocks) created");
        statmt.close();
    }

    public static void AddBlock(int x, int y, int z, int dimension, int blockid, String playername) throws SQLException {
                String request = "INSERT INTO 'placedLimitedBlocks' ('type', 'x', 'y', 'z', 'dimension','player') " +
                         "VALUES ("
                         + blockid + ","
                         + x + ","
                         + y + ","
                         + z + ","
                         + dimension + ",'"
                         + playername + "');";
        Statement statmt = conn.createStatement();
        statmt = conn.createStatement();
        statmt.execute(request);
        System.out.println("BPL: Placed block (" + x + "," + y + "," + z + ")");
        statmt.close();
    }

    public static void RemoveBlock(int x, int y, int z, int dimension) throws SQLException {
        String request= "DELETE FROM 'placedLimitedBlocks' " +
                        "WHERE x=" + x +
                        " AND y=" + y +
                        " AND z=" + z +
                        " AND dimension=" + dimension+";";
        Statement statmt = conn.createStatement();
        statmt = conn.createStatement();
        statmt.execute(request);
        System.out.println("BPL: Removed block (" + x + "," + y + "," + z + ")");
        statmt.close();
    }

    public static int CountPlaced(int type, String playername) throws SQLException {
        String request = "SELECT COUNT() FROM 'placedLimitedBlocks' " +
                         "WHERE type=" + type + " AND player='"+playername+"';";
        ResultSet resSet;
        Statement statmt = conn.createStatement();
        resSet = statmt.executeQuery(request);
        int counted = resSet.getInt("COUNT()");
        resSet.close();
        statmt.close();
        return counted;
    }

    public static void CloseDB() throws SQLException {
        conn.close();
        System.out.println("BPL: Disconnected from database");
    }

}
