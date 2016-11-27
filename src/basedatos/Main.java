package basedatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import com.mysql.fabric.xmlrpc.base.Array;

public class Main {

	
	public ArrayList<NodoSimple> contenedorNodos;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			Statement st=AccesoDB.getConnection().createStatement();
				int nivelBase;
			
			
			//Inicializacion de listas auxiliares 
			ArrayList<Nodo> listaNodos=new ArrayList<>();
			ArrayList<Nodo> idNodoBuscar=new ArrayList<>();  // lista con los nodos a los cuales se les buscara sus nodos padres e hijos 
			//Inicializacion de un objeto nodo con informacion del nodo del cual  deseamos obtener
			// su rama , seteado en duro 
			Nodo nodoPadreDeTodos=new Nodo();               
			nodoPadreDeTodos.nodoId=733;                    
			idNodoBuscar.add(nodoPadreDeTodos);            
			int nivel=0;
			ArrayList<NodoSimple> contenedorNodos=getContenedorNodos(st); 

			
			
			
			
			//Segmento de código que me permite obtener todos los  nodos padres de un nodo
						
			while(idNodoBuscar.size()>0){
				nivel=nivel-1;
				ArrayList<Nodo> auxBusqueda=new ArrayList<>();

				for (Nodo idBuscar : idNodoBuscar) {   // Se itera sobre la lista de nodos a buscar
					Nodo nodoPadre=idBuscar;
					// se obtiene las reglas de asociación del nodo  iterado 
					ArrayList<NodoSimple> hijosPadre=buscarPadresNodo(nodoPadre.nodoId, contenedorNodos); nodo a   
					//Se convierte esas reglas de asociacion en un objeto nodo simple y se agrega al listado de nodos padres
					for (NodoSimple nodoSimple : hijosPadre) {
						Nodo nodo=new Nodo();
						nodo.nodoId=nodoSimple.nodoSi;
						nodo.valor=nodoSimple.valorEntonces;
						nodo.nodoPadre=nodoPadre.nodoId;
						nodo.nivel=nivel;
						nodoPadre.listaNodoPadres.add(nodo);
						auxBusqueda.add(nodo);
					}
					
				}
				
				idNodoBuscar=auxBusqueda;   // se inicializa el listado de nodos a buscar con nuevos nodos
			
				}
			
			
			//se calcula el nivel del nodo al cual estamos buscando sus ramas
			nivel=-1*nivel;
			nivelBase=nivel;
			idNodoBuscar.add(nodoPadreDeTodos);
			
			//Buscar hijos
			while(idNodoBuscar.size()>0){
				nivel=nivel+1;
				ArrayList<Nodo> auxBusqueda=new ArrayList<>();

				for (Nodo idBuscar : idNodoBuscar) { // Se itera sobre la lista de nodos a buscar
					Nodo nodoPadre=idBuscar;
					// se obtiene las reglas de asociación del nodo  iterado 
					ArrayList<NodoSimple> hijosPadre=buscarHijosNodo(nodoPadre.nodoId, contenedorNodos);
					//Se convierte esas reglas de asociacion en un objeto nodo simple y se agrega al listado de nodos padres
					for (NodoSimple nodoSimple : hijosPadre) {
						Nodo nodo=new Nodo();
						nodo.nodoId=nodoSimple.nodoEntonces;
						nodo.valor=nodoSimple.valor;
						nodo.nodoPadre=nodoPadre.nodoId;
						nodo.nivel=nivel;
						nodoPadre.listaNodoHijos.add(nodo);
						auxBusqueda.add(nodo);
					}
					
				}
				 
				idNodoBuscar=auxBusqueda;       //se inicializa el listado de nodos a buscar
			
				}
			
			
			nodoPadreDeTodos.nivel=nivelBase;
			nodoPadreDeTodos.estabilizarNivelPadres(nivelBase);  
			
			System.out.println(nodoPadreDeTodos); //se imprime el resultado
		
		
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	
	/*
	Método que permite obtener el id del nodo que es padre de todos los nodos
	*/
	public static Integer getNodoPadreTodos() throws ClassNotFoundException, SQLException{
		Statement st=AccesoDB.getConnection().createStatement();
		ResultSet resultSet=st.executeQuery("select nodosi from regla where !(idnodosi in (select idnodoentonces from regla)) and iddiagrama28");
		int valorReturn=0;
		
		while(resultSet.next()){
			
				valorReturn=resultSet.getInt(1);
		}
		return valorReturn;
	}
	
	
	
	/*
	*Método que realiza la busqueda en la base de datos para obtener todas las reglas de 
	asociación de un par nodo padre, nodo hijo
	*@param st ;para poder buscar en la base de datos
	*@return lista del objeto de NodoSimple
	*/
	public static ArrayList<NodoSimple>  getContenedorNodos(Statement st) throws SQLException{
		ArrayList<NodoSimple> listaNodoSimples=new ArrayList<>();
		ResultSet resultSet=st.executeQuery("select n.idnodo,r.condicion , "
				+ "n.valor,r.idnodoentonces,n2.valor from regla r inner join nodo n on n.idnodo=r.idnodosi "
				+ "inner join nodo n2 on r.idnodoentonces=n2.idnodo");
		

		while(resultSet.next()){
				NodoSimple nodo=new NodoSimple();
				nodo.nodoSi=resultSet.getInt(1);
				nodo.valor=resultSet.getString(3);
				nodo.nodoEntonces=resultSet.getInt(4);
				nodo.valorEntonces=resultSet.getString(5);
				listaNodoSimples.add(nodo);
				
				
				
		}
		
		return listaNodoSimples;
	}
	
	
	
	/*
	*Método que permite obtener los nodos que se encuentran un nivel más abajo de un nodo dado,  es decir
	nos permite obtener los nodos hijos de un nodo
	*@param idNodo ,id del nodo del cual deseamos obtener 
	*@param listaContenedora , lista contenedoras de nodos  en la cual se hara la busqueda
	*@return lista de nodos con los nodos hijos del nodo dado
	*/
	public static ArrayList<NodoSimple> buscarHijosNodo(int idNodo,ArrayList<NodoSimple> listaContenedora){
		ArrayList<NodoSimple> listaNodo=new ArrayList<>();
		for (int i = 0; i < listaContenedora.size(); i++) {
			if (listaContenedora.get(i).nodoSi==idNodo) {
				listaNodo.add(listaContenedora.get(i));
			}
		
			
		}

		return listaNodo;
		
	}
	
	
	
	/*
	*Método que permite obtenre los nodos que se encuentran un nivel más arriba de un nodo dado,  es decir
	nos permite obtener los nodos padres de un nodo
	*@param idNodo ,id del nodo del cual deseamos obtener 
	*@param listaContenedora , lista contenedoras de nodos  en la cual se hara la busquera
	*@return lista de nodos con los nodos padres del nodo dado
	*/
	public static ArrayList<NodoSimple> buscarPadresNodo(int idNodo,ArrayList<NodoSimple> listaContenedora){
		ArrayList<NodoSimple> listaNodo=new ArrayList<>();
		for (int i = 0; i < listaContenedora.size(); i++) {
			if (listaContenedora.get(i).nodoEntonces==idNodo) {
				listaNodo.add(listaContenedora.get(i));
			}
		
			
		}

		return listaNodo;
		
	}

}
