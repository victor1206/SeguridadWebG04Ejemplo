/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sysseguridadg04.accesoadatos;

/**
 *
 * @author victo
 */

import java.util.*;
import java.sql.*;
import sysseguridadg04.entidadesdenegocio.*;

public class RolDAL {
    static String obtenerCampos()
    {
        return "r.Id, r.Nombre";
    }
    
    private static String obtenerSelect(Rol pRol)
    {
        String sql;
        sql = "Select ";
        if(pRol.getTop_aux() > 0 && 
           ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER)
        {
            sql += "Top " + pRol.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " From Rol r");
        return sql;
    }
    
    private static String agregarGroupBy(Rol pRol)
    {
        String sql;
        sql = " Order by r.Id Desc ";
        if(pRol.getTop_aux() > 0 && 
           ComunDB.TIPODB == ComunDB.TipoDB.MYSQL)
        {
            sql += " Limit " + pRol.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(Rol pRol) throws Exception
    {
        int result;
        String sql;
        try(Connection conn = ComunDB.obtenerConexion();)
        {
            sql = "Insert Into Rol(Nombre) Values(?)";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);)
            {
                ps.setString(1, pRol.getNombre());
                result = ps.executeUpdate();
                ps.close();
            }
            catch(SQLException ex)
            {
                throw ex;
            }
            conn.close();
        }
        catch(SQLException ex)
        {
            throw ex;
        }
        return result;
    }
    
    public static int modificar(Rol pRol) throws Exception
    {
        int result;
        String sql;
        try(Connection conn = ComunDB.obtenerConexion();)
        {
            sql = "Update Rol Set Nombre = ? Where Id = ?";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);)
            {
                ps.setString(1, pRol.getNombre());
                ps.setInt(2, pRol.getId());
                result = ps.executeUpdate();
                ps.close();
            }
            catch(SQLException ex)
            {
                throw ex;
            }
            conn.close();
        }
        catch(SQLException ex)
        {
            throw ex;
        }
        return result;
    }
    
    public static int eliminar(Rol pRol) throws Exception
    {
        int result;
        String sql;
        try(Connection conn = ComunDB.obtenerConexion();)
        {
            sql = "Delete From Rol Where Id = ?";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);)
            {
                ps.setInt(1, pRol.getId());
                result = ps.executeUpdate();
                ps.close();
            }
            catch(SQLException ex)
            {
                throw ex;
            }
            conn.close();
        }
        catch(SQLException ex)
        {
            throw ex;
        }
        return result;
    }
    
    static int asignarDatosResultSet(Rol pRol, ResultSet pResSet, int pIndex) throws Exception
    {
        pIndex++;
        pRol.setId(pResSet.getInt(pIndex));
        pIndex++;
        pRol.setNombre(pResSet.getString(pIndex));
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Rol> pRoles) throws Exception
    {
        try(ResultSet resultSet = ComunDB.obtenerResultSet(pPS);)
        {
            while(resultSet.next())
            {
                Rol rol = new Rol();
                asignarDatosResultSet(rol, resultSet,0);
                pRoles.add(rol);
            }
            resultSet.close();
        }
        catch(SQLException ex)
        {
            throw ex;
        }
    }
    
    public static Rol obtenerPorId(Rol pRol) throws Exception
    {
        Rol rol = new Rol();
        ArrayList<Rol> roles = new ArrayList();
        String sql;
        try(Connection conn = ComunDB.obtenerConexion();)
        {
            sql = obtenerSelect(pRol);
            sql += "Where r.Id = ?";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);)
            {
                ps.setInt(1, pRol.getId());
                obtenerDatos(ps, roles);
                ps.close();
            }
            catch(SQLException ex)
            {
                throw ex;
            }
            conn.close();
        }
        catch(SQLException ex)
        {
            throw ex;
        }
        
        if(roles.size() > 0)
        {
            rol = roles.get(0);
        }
        return rol;
    }
    
    public static ArrayList<Rol> obtenerTodos() throws Exception
    {
        ArrayList<Rol> roles = new ArrayList();
        String sql;
        try(Connection conn = ComunDB.obtenerConexion();)
        {
            sql = obtenerSelect(new Rol());
            sql += agregarGroupBy(new Rol());
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);)
            {
                obtenerDatos(ps, roles);
                ps.close();
            }
            catch(SQLException ex)
            {
                throw ex;
            }
            conn.close();
        }
        catch(SQLException ex)
        {
            throw ex;
        }
        
        return roles;
    }
    
    static void querySelect(Rol pRol, ComunDB.UtilQuery pUtilQuery) throws SQLException
    {
        PreparedStatement ps = pUtilQuery.getStatement();
        if(pRol.getId() > 0)
        {
            pUtilQuery.AgregarWhereAnd(" r.Id = ? ");
            if(ps != null)
            {
                ps.setInt(pUtilQuery.getNumWhere(), pRol.getId());
            }
        }
        
        if(pRol.getNombre() != null && 
           pRol.getNombre().trim().isEmpty() == false)
        {
            pUtilQuery.AgregarWhereAnd(" r.Nombre Like ? ");
            if(ps != null)
            {
                ps.setString(pUtilQuery.getNumWhere(), 
                "%" + pRol.getNombre() + "%");
            }
        }
        
    }
    
    public static ArrayList<Rol> buscar(Rol pRol) throws Exception
    {
        ArrayList<Rol> roles = new ArrayList();
        String sql;
        try(Connection conn = ComunDB.obtenerConexion();)
        {
            sql = obtenerSelect(pRol);
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = 
                    comundb.new UtilQuery(sql,null,0);
            querySelect(pRol, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarGroupBy(new Rol());
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);)
            {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pRol, utilQuery);
                obtenerDatos(ps, roles);
                ps.close();
            }
            catch(SQLException ex)
            {
                throw ex;
            }
            conn.close();
        }
        catch(SQLException ex)
        {
            throw ex;
        }
        
        return roles;
    }
}
