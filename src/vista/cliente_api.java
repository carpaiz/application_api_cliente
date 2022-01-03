/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import javax.swing.table.DefaultTableModel;
import java.io.OutputStream;
/**
 *
 * @author paiz2
 */
public class cliente_api {
    private String  url_api = "https://localhost:5001/api/clientes";
    private String get(){
    String salida ="";
    try{
        URL url = new URL(url_api);
        HttpURLConnection c_api = (HttpURLConnection) url.openConnection();
        c_api.setRequestMethod("GET");
        c_api.setRequestProperty("Accept", "application/json");
        if (c_api.getResponseCode() !=200){
            salida = "No se pudo acceder a la api";
        }else{
        InputStreamReader entrada = new InputStreamReader(c_api.getInputStream());
        BufferedReader lectura = new BufferedReader(entrada);
        salida = lectura.readLine();
        }
        c_api.disconnect();
        
    }catch(IOException ex){
        System.out.println("Error:"+ex.getMessage());
    }
    return salida;
    }
    public DefaultTableModel leer(){
    DefaultTableModel tabla = new DefaultTableModel();
     try{ 
     String encabezado[] = {"id","Nit","Nombres","Apellidos","Direccion","Telefono","Nacimiento"};
     tabla.setColumnIdentifiers(encabezado);
     JSONArray arreglo = new JSONArray(get());
     String datos[] = new String[7];
     for (int indice = 0;indice < arreglo.length();indice++){
     JSONObject atributo = arreglo.getJSONObject(indice);
     
         datos[0] = String.valueOf(atributo.getInt("id_cliente")) ;
         datos[1] = atributo.getString("nit");
         datos[2] = atributo.getString("nombres");
         datos[3] = atributo.getString("apellidos");
         datos[4] = atributo.getString("direccion");
         datos[5] = atributo.getString("telefono");
         datos[6] = atributo.getString("fecha_nacimiento");
         tabla.addRow(datos);
         
     }
     }catch(JSONException ex){
        System.out.println("Error:"+ex.getMessage());
    }
    return tabla;
    }
  public int post(){
  int salida = 0;
   try{
        URL url = new URL(url_api);
        HttpURLConnection c_api = (HttpURLConnection) url.openConnection();
        c_api.setRequestMethod("POST");
        c_api.setRequestProperty("Content-Type", "application/json; utf-8");
        c_api.setDoOutput(true); 
        String jsonS = "{\"nit\":\"1234\",\n" +
"    \"nombres\":\"Jose Jose\",\n" +
"    \"apellidos\":\"Lopez Lopez\",\n" +
"    \"direccion\":\"Direccion\",\n" +
"    \"telefono\":\"555\",\n" +
"    \"fecha_nacimiento\":\"1995-01-01\"\n" +
"}";
        OutputStream os = c_api.getOutputStream();
        os.write(jsonS.getBytes());
        os.flush();
        if (c_api.getResponseCode() !=200){
            System.out.println("No se pudo acceder a la api") ;
        }else{
        salida = 1;
        }
        c_api.disconnect();
        
        
        }catch(IOException ex){
        System.out.println("Error:"+ex.getMessage());
    }
  return salida;
  }  
}
