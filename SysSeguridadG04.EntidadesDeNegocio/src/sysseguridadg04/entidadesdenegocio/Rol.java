/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sysseguridadg04.entidadesdenegocio;

/**
 *
 * @author victo
 */
import java.util.ArrayList;

public class Rol {
    private int id;
    private String nombre;
    private int top_aux;
    private ArrayList<Usuario> usuarios;

    public Rol() {
    }

    public Rol(int id, String nombre, int top_aux, ArrayList<Usuario> usuarios) {
        this.id = id;
        this.nombre = nombre;
        this.top_aux = top_aux;
        this.usuarios = usuarios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
