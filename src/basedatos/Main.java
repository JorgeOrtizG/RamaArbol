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
			
			ArrayList<Nodo> listaNodos=new ArrayList<>();
			ArrayList<Nodo> idNodoBuscar=new ArrayList<>();
			Nodo nodoPadreDeTodos=new Nodo();
			nodoPadreDeTodos.nodoId=733;
			idNodoBuscar.add(nodoPadreDeTodos);
			int nivel=0;
			ArrayList<NodoSimple> contenedorNodos=getContenedorNodos(st);

			
			
			
			
			//BuscarPadres
			
			//idNodoBuscar.add(nodoPadreDeTodos);
			
			while(idNodoBuscar.size()>0){
				nivel=nivel-1;
				ArrayList<Nodo> auxBusqueda=new ArrayList<>();

				for (Nodo idBuscar : idNodoBuscar) {
					Nodo nodoPadre=idBuscar;
					ArrayList<NodoSimple> hijosPadre=buscarPadresNodo(nodoPadre.nodoId, contenedorNodos);
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
				
				idNodoBuscar=auxBusqueda;
			
				}
			
			//Buscar hijos
			
			nivel=-1*nivel;
			nivelBase=nivel;
			idNodoBuscar.add(nodoPadreDeTodos);
			
			while(idNodoBuscar.size()>0){
				nivel=nivel+1;
				ArrayList<Nodo> auxBusqueda=new ArrayList<>();

				for (Nodo idBuscar : idNodoBuscar) {
					Nodo nodoPadre=idBuscar;
					ArrayList<NodoSimple> hijosPadre=buscarHijosNodo(nodoPadre.nodoId, contenedorNodos);
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
				
				idNodoBuscar=auxBusqueda;
			
				}
			nodoPadreDeTodos.nivel=nivelBase;
			nodoPadreDeTodos.estabilizarNivelPadres(nivelBase);
			
			System.out.println(nodoPadreDeTodos);
		
		
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static Integer getNodoPadreTodos() throws ClassNotFoundException, SQLException{
		Statement st=AccesoDB.getConnection().createStatement();
		ResultSet resultSet=st.executeQuery("select nodosi from regla where !(idnodosi in (select idnodoentonces from regla)) and iddiagrama28");
		int valorReturn=0;
		
		while(resultSet.next()){
			
				valorReturn=resultSet.getInt(1);
		}
		return valorReturn;
	}
	
	
	
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
	
	
	public static ArrayList<NodoSimple> buscarHijosNodo(int idNodo,ArrayList<NodoSimple> listaContenedora){
		ArrayList<NodoSimple> listaNodo=new ArrayList<>();
		for (int i = 0; i < listaContenedora.size(); i++) {
			if (listaContenedora.get(i).nodoSi==idNodo) {
				listaNodo.add(listaContenedora.get(i));
			}
		
			
		}

		return listaNodo;
		
	}
	
	
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
