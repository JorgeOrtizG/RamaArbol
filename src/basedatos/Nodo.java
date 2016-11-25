package basedatos;

import java.util.ArrayList;

import com.mysql.fabric.xmlrpc.base.Array;

public class Nodo {
	String valor;
	int nodoId;
	String condicion;
	int nodoPadre;
	int nivel;
	ArrayList<Nodo> listaNodoHijos;
	ArrayList<Nodo> listaNodoPadres;
	public Nodo() {
		// TODO Auto-generated constructor stub
		listaNodoHijos=new ArrayList<>();
		listaNodoPadres=new ArrayList<>();
	}
	@Override
	public String toString() {
		return "{'Id':'" + nodoId +"','valor':'"+valor+"','condicion':'"+condicion+"','nivel':'"+nivel+"',"
				+ "'listaNodoHijos':"
				+ listaNodoHijos +",'listaNodoPadres':"+listaNodoPadres+ "}";
	}
	
	
	public void estabilizarNivelPadres(int base){
		for (Nodo nodo : listaNodoPadres) {
			nodo.nivel=nodo.nivel+base;
			nodo.estabilizarNivelPadres(base);
		}
		
	}
	
	

}
