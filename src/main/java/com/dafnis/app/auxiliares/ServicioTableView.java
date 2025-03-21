package com.dafnis.app.auxiliares;

import com.dafnis.app.controllers.Controlador;
import com.dafnis.app.models.Cuento;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.SQLException;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import java.sql.ResultSet;

public class ServicioTableView{

	static private int numeroDeRegistros;
	private TextField textfieldTitulo;
	private TextField textfieldAutor;
	private ComboBox<String> comboboxNacionalidad;
	private ComboBox<String> comboboxCategoria;

	static public int getNumeroDeRegistros(){
		return numeroDeRegistros;
	}

	public void setComponentesDeBusqueda(TextField textfieldTitulo, TextField textfieldAutor, ComboBox comboboxNacionalidad, ComboBox comboboxCategoria){
		this.textfieldTitulo = textfieldTitulo;
		this.textfieldAutor = textfieldAutor;
		this.comboboxNacionalidad = comboboxNacionalidad;
		this.comboboxCategoria = comboboxCategoria;
	}

	public String prepararConsulta(){
		String inicioConsulta = "select id, title, author, category, country, years from cuentos";
		StringBuilder sb = new StringBuilder(inicioConsulta);
		if(!textfieldTitulo.getText().trim().isEmpty()){
			sb.append( " where title = \"" + textfieldTitulo.getText() + "\"");
		}else if(!textfieldAutor.getText().trim().isEmpty()){
				sb.append(" where author = \"" + textfieldAutor.getText() + "\"");
				if(comboboxNacionalidad.getValue() != null && !comboboxNacionalidad.getValue().isEmpty()){
					sb.append(" and country = \"" + comboboxNacionalidad.getValue() + "\"");
					if(comboboxCategoria.getValue() != null && !comboboxCategoria.getValue().isEmpty()){
						sb.append(" and category = \"" + comboboxCategoria.getValue() + "\"");
					}
				}else if(comboboxCategoria.getValue() != null && !comboboxCategoria.getValue().isEmpty()){
						sb.append(" and category = \"" + comboboxCategoria.getValue() + "\"");
			}
		}else if(comboboxNacionalidad.getValue() != null && !comboboxNacionalidad.getValue().isEmpty()){
					sb.append(" where country = \"" + comboboxNacionalidad.getValue() + "\"");
					if(comboboxCategoria.getValue() != null && !comboboxCategoria.getValue().isEmpty()){
						sb.append(" and category = \"" + comboboxCategoria.getValue() + "\"");
					}
		}else if(comboboxCategoria.getValue() != null && !comboboxCategoria.getValue().isEmpty()){
						sb.append(" where category = \"" + comboboxCategoria.getValue() + "\"");
			}
		sb.append(";");
		return sb.toString();
	}

	public ObservableList<Cuento> prepararDatos(ResultSet resultSet){
		ObservableList<Cuento> observableList = FXCollections.observableArrayList();
		numeroDeRegistros = 0;
		try{
			while(resultSet.next()){
                observableList.add(new Cuento(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("category"), resultSet.getString("country"), resultSet.getString("years")));
				numeroDeRegistros++;
	        }
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
        return observableList;
	}
}
