/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sysseguridadg04.accesoadatos;

/**
 *
 * @author victo
 */
import java.sql.*;

public class ComunDB {
    class TipoDB{
        static final int SQLSERVER=1;
        static final int MYSQL=2;
    }
    
    static int TIPODB = TipoDB.SQLSERVER;
    static String connectionURL = "jddc:sqlserver://localhost:1433;"
            + "database=dbPracticaG04;"
            + "user=sa;"
            + "password=;"
            + "loginTimeout=30;encrypt=false;trustServerCertificate=false;";
            //+ "integratedSecurity=true;";
    
    public static Connection obtenerConexion() throws SQLException
    {
        DriverManager.registerDriver( new 
        com.microsoft.sqlserver.jdbc.SQLServerDriver()
        );
        Connection conect = DriverManager.getConnection(
                connectionURL);
        return conect;
    }
    
    public static Statement createStatement(Connection pCon) throws SQLException
    {
        Statement st = pCon.createStatement();
        return st;
    }
    
    public static PreparedStatement createPreparedStatement(Connection pCon, String pSql) throws SQLException
    {
        PreparedStatement statement = pCon.prepareStatement(
                pSql);
        return statement;
    }
    
    public static ResultSet obtenerResultSet(Statement pStatement, String pSql) throws SQLException
    {
        ResultSet resultSt = pStatement.executeQuery(pSql);
        return resultSt;
    }
    
    public static ResultSet obtenerResultSet(PreparedStatement pPreparedSta) throws SQLException
    {
        ResultSet resultSt = pPreparedSta.executeQuery();
        return resultSt;
    }
    
    class UtilQuery{
        private String SQL;
        private PreparedStatement statement;
        private int numWhere;

        public UtilQuery() {
        }

        public UtilQuery(String SQL, PreparedStatement statement, int numWhere) {
            this.SQL = SQL;
            this.statement = statement;
            this.numWhere = numWhere;
        }

        public String getSQL() {
            return SQL;
        }

        public void setSQL(String SQL) {
            this.SQL = SQL;
        }

        public PreparedStatement getStatement() {
            return statement;
        }

        public void setStatement(PreparedStatement statement) {
            this.statement = statement;
        }

        public int getNumWhere() {
            return numWhere;
        }

        public void setNumWhere(int numWhere) {
            this.numWhere = numWhere;
        }
        
        public void AgregarWhereAnd(String pSql){
            if(this.SQL != null)
            {
                if(this.numWhere == 0)
                {
                    this.SQL += " Where ";
                }
                else
                {
                    this.SQL += " And ";
                }
                this.SQL += pSql;
            }
        this.numWhere++;
        }
        
    }   
}
