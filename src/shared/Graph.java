package shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Graf generic
 * @param <T> El tipus del pes de les arestes
 */
public class Graph<K,T> {
	
	private ArrayList<K> vertexs;
	private HashMap<K,ArrayList<Edge<K,T>>> edges;
	
	/**
	 * Inicialitza les llistes de vertexs i d'arestes
	 */
	public Graph() {
		vertexs = new ArrayList<K>();
		edges = new HashMap<K,ArrayList<Edge<K,T>>>();
	}
	
	/**
	 * Constructor per a clonar grafs
	 * @param toClone Graph a clonar
	 */
	public Graph (Graph<K,T> toClone)
        {
            vertexs = toClone.getAllVertex();
            edges = toClone.getAllEdges();
	}
	
	/**
	 * Override de l'equal. Compara l'objecte 'o' amb el Graph<K,T> nostre.
	 */
	@Override
    public boolean equals(Object o)
    {
		// Si son el mateix objecte, true
		if (o == this) return true;
		
		// Si l'objecte no es una instancia de Graph, false
		if (!(o instanceof Graph)) return false;
		
		try {
			// Convertir-lo a l'objecte que es realment
			Graph<K,T> g = (Graph<K,T>) o;
			// Per cada vertex del nostre objecte, mirar si existeix en l'objecte i si te les mateixes arestes
			for (K vertex : vertexs) {
				// Si no te el vertex, false
				if (!g.vertexs.contains(vertex))
					return false;
				
				// Agafar les dues llistes d'arestes i mirar si son iguals
				ArrayList<Edge<K,T>> edgesG = g.getEdges(vertex);
				ArrayList<Edge<K,T>> edgesC = getEdges(vertex);
				
				for (Edge<K,T> eG : edgesG) {
					boolean found = false;
					for (Edge<K,T> eC : edgesC) {
						found = (eC.getKey() == eG.getKey() && eC.getValue() == eG.getValue());
					}
					
					if (!found) return false;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Afegim un vertex al graf
	 * @param v El vertex
	 */
	public void addVertex(K v) {
		// Afegim el vertex a la llista de vertexs
		vertexs.add(v);
		
		// Inicialitzem al hashMap la llista d'arestes d'aquest vertex
		ArrayList<Edge<K,T>> vEdges = new ArrayList<Edge<K,T>>();
		edges.put(v, vEdges);
	}
	
	/**
	 * Afegeix una aresta al graf
	 * @param a Un vertex de l'aresta
	 * @param b L'altre vertex de l'aresta
	 * @param v El pes de l'aresta
	 */
	public void addEdge(K a, K b, T v) {
		// Creem l'aresta de vertex 'a' a 'b' amb pes 'v'
		Edge<K,T> edgeAB = new Edge<K,T>(b,v);
		// Obtenim la llista d'arestes que surten del vertex 'a'
		ArrayList<Edge<K,T>> vAEdges = edges.get(a);
		// Afegim l'aresta previament creada
		vAEdges.add(edgeAB);
		
		// Creem l'aresta de vertex 'b' a 'a' amb pes 'v'
		Edge<K,T> edgeBA = new Edge<K,T>(a,v);
		// Obtenim la llista d'arestes que surten del vertex 'b'
		ArrayList<Edge<K,T>> vBEdges = edges.get(b);
		// Afegim l'aresta previament creada
		vBEdges.add(edgeBA);
	}
	
	/**
	 * Obte la llista d'arestes del vertex 'v'
	 * @param v El vertex
	 * @return ArrayList amb les arestes
	 */
	public ArrayList<Edge<K,T>> getEdges(K v) {
		if (edges.containsKey(v))
			return edges.get(v);
		
		// No existeix el vertex a la llista de vertexs, retornar ArrayList buida
		return new ArrayList<Edge<K,T>>();
	}
	
	/**
	 * Obte la llista de vertexs
	 * @return ArrayList dels vertexs
	 */
	public ArrayList<K> getVertexs() {
		//return vertexs;
            return this.vertexs;
	}
	
	/**
	 * Obte la llista de veins
	 * @param v Vertex origen
	 * @return Llista de vertexs veins al vertex origen.
	 */
	public ArrayList<K> getNeighbors(K v) {
		ArrayList<K> neighs = new ArrayList<K>();
	
		// Afegir el desti de cada aresta que surt de 'v'
		for (Edge<K,T> edge : getEdges(v)) 
			neighs.add(edge.getKey());
		
		return neighs;
	}
        
        /**
         *
         * @return
         */
        public HashMap<K,ArrayList<Edge<K,T>>> getAllEdges()
        {
            return new HashMap(this.edges);
        }
	
        public ArrayList<K> getAllVertex()
        {
            return new ArrayList(this.vertexs);
        }
	/**
	 * Elimina una aresta del graf
	 * @param o Vertex origen de l'aresta
	 * @param d Vertex desti de l'aresta
	 */
	public void removeEdge(K o, K d) {
            edges.get(o).remove(d);
	}
}