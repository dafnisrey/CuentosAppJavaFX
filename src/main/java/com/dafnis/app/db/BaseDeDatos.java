package com.dafnis.app.db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class BaseDeDatos{
	static Connection conn;
    static BaseDeDatos obj;
    private int filasAfectadas;

    private BaseDeDatos(){}
    static public BaseDeDatos getObj(){
        if(obj == null){
            obj = new BaseDeDatos();
        }
        return obj;
    }

    public void conectar(){
        String url = "jdbc:sqlite::resource:cuentos_db";
        try{
            conn = DriverManager.getConnection(url);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public ResultSet realizarConsulta(String str){
        ResultSet resultado = null;
        try{
            var consultaPreparada = conn.prepareStatement(str);
            resultado = consultaPreparada.executeQuery();
        }catch(SQLException e){
                System.out.println(e.getMessage());
        }
        return resultado;
    }

    public void realizarInsercion(String titulo, String autor, String nacionalidad, String categoria, String contenido){
        String inicioConsulta = "insert into cuentos(id, title, author, country, years, category, content) values (?, ?, ?, ?, ?, ?, ?)";
        int id = consultarUltimoId();
        id++;
        try{
            PreparedStatement consultaPreparada = conn.prepareStatement(inicioConsulta);
            consultaPreparada.setInt(1, id);
            consultaPreparada.setString(2, titulo);
            consultaPreparada.setString(3, autor);
            consultaPreparada.setString(4, nacionalidad);
            consultaPreparada.setString(5, "2025");
            consultaPreparada.setString(6, categoria);
            consultaPreparada.setString(7, contenido);
            filasAfectadas = consultaPreparada.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void borrarTodosLosNuevosRegistros(){
        String inicioConsulta = "delete from cuentos where id >= 7240;";
        try{
            PreparedStatement consultaPreparada = conn.prepareStatement(inicioConsulta);
            filasAfectadas = consultaPreparada.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public int consultarUltimoId(){
        String consultaUltimoId = "select max(id) from cuentos;";
        int ultimoId = 0;
        try{
            var consultaUltimo = conn.prepareStatement(consultaUltimoId);
            ResultSet resultadoUltimoId = consultaUltimo.executeQuery();
            resultadoUltimoId.next();
            ultimoId = resultadoUltimoId.getInt(1);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return ultimoId;
    }

    public void desconectar(){
        try{
            conn.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
