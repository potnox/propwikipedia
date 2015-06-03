package domain.grafos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Grafo de entrada se encarga de almacenar de forma eficiente todos los datos
 * referidos a las categorias, paginas y sus respectivos enlaces.
 *
 * @author Javier López Calderón
 * @version 1.0
 * @since 01/06/2015
 */
public class GrafoEntrada implements Cloneable
{
    private Integer categoryId;
    private Integer pageId;
    private int edgeSize;
    private int hashCapacity;
    private HashMap<Integer, Categoria> indexCategoria;
    private HashMap<Integer, Pagina> indexPagina;
    private HashMap<Categoria, Integer> categoriaIndex;
    private HashMap<Pagina, Integer> paginaIndex;
    private HashMap<Integer, ArrayList<Arch>> csupcEdges;
    private HashMap<Integer, ArrayList<Arch>> csubcEdges;
    private HashMap<Integer, ArrayList<Arch>> cpEdges;
    private HashMap<Integer, ArrayList<Arch>> pcEdges;

    /**
     * Constructor por defecto
     */
    public GrafoEntrada()
    {
        this.categoryId = 0;
        this.pageId = 0;
        this.edgeSize = 0;
        this.hashCapacity = 5814;
        this.indexCategoria = new HashMap(this.hashCapacity);
        this.indexPagina = new HashMap(this.hashCapacity * 3);
        this.categoriaIndex = new HashMap(this.hashCapacity);
        this.paginaIndex = new HashMap(this.hashCapacity * 3);
        this.csubcEdges = new HashMap(this.hashCapacity);
        this.csupcEdges = new HashMap(this.hashCapacity);
        this.cpEdges = new HashMap(this.hashCapacity);
        this.pcEdges = new HashMap(this.hashCapacity);
    }

    /**
     * Constructor con posibilidad de incluir un hash concreto para las
     * estructuras de datos que emplea. Por defecto se emplea un factor de carga
     * de 0,75
     *
     * @param hashCapacity entero con la capacidad para las estructuras
     */
    public GrafoEntrada(int hashCapacity)
    {
        this.categoryId = 0;
        this.pageId = 0;
        this.edgeSize = 0;
        this.hashCapacity = hashCapacity;
        this.indexCategoria = new HashMap(this.hashCapacity);
        this.indexPagina = new HashMap(this.hashCapacity * 3);
        this.categoriaIndex = new HashMap(this.hashCapacity);
        this.paginaIndex = new HashMap(this.hashCapacity * 3);
        this.csubcEdges = new HashMap(this.hashCapacity);
        this.csupcEdges = new HashMap(this.hashCapacity);
        this.cpEdges = new HashMap(this.hashCapacity);
        this.pcEdges = new HashMap(this.hashCapacity);
    }

    /**
     * Obtiene el numero total de aristas presentes en el grafo
     *
     * @return numero de aristas
     */
    public int getNumberEdges()
    {
        return this.edgeSize;
    }

    /**
     * Obtiene el numero de categorias que hay
     *
     * @return numero de categorias
     */
    public int getCategorySize()
    {
        return this.indexCategoria.size();
    }

    /**
     * Obtiene el numero de paginas que hay
     *
     * @return numero de paginas
     */
    public int getPageSize()
    {
        return this.indexPagina.size();
    }

    /**
     * Cambia el nombre de una pagina
     *
     * @param page numero de la pagina a cambiar
     * @param change nombre nuevo
     */
    public void changePage(Integer page, String change)
    {
        Pagina pag = this.indexPagina.get(page);
        pag.setNombre(change);
        this.indexPagina.put(page, pag);
        this.paginaIndex.put(pag, page);
    }

    /**
     * Cambia el nombre de una categoria
     *
     * @param category numero de la categoria a cambiar
     * @param change nombre nuevo
     */
    public void changeCategory(Integer category, String change)
    {
        Categoria cat = this.indexCategoria.get(category);
        cat.setNombre(change);
        this.indexCategoria.put(category, cat);
        this.categoriaIndex.put(cat, category);
    }

    /**
     * Obtiene todas las categorias que hay
     *
     * @return lista de categorias
     */
    public ArrayList<Integer> getCategories()
    {
        return new ArrayList(this.indexCategoria.keySet());
    }

    /**
     * Obtiene todas las paginas que hay
     *
     * @return lista de paginas
     */
    public ArrayList<Integer> getPages()
    {
        return new ArrayList(this.indexPagina.keySet());
    }

    /**
     * Obtiene las categorias que son adyacentes a una categoria concreta
     *
     * @param node categoria sobre la que buscar adyacentes
     * @return lista de adyacentes que son categoria
     */
    public ArrayList<Integer> getCategoriesAdyacentCategories(Integer node)
    {
        ArrayList<Integer> response = new ArrayList();
        int temp;
        if(this.csubcEdges.containsKey(node))
        {
            for(Arch arc : this.csubcEdges.get(node))
            {
                temp = arc.getDestiny();
                if(!response.contains(temp))
                {
                    response.add((Integer) temp);
                }
            }
        }
        if(this.csupcEdges.containsKey(node))
        {
            for(Arch arc : this.csupcEdges.get(node))
            {
                temp = arc.getDestiny();
                if(!response.contains(temp))
                {
                    response.add((Integer) temp);
                }
            }
        }
        return response;
    }

    /**
     * Obtiene las categorias que son adyacentes a una pagina concreta
     *
     * @param node pagina sobre la que buscar adyacentes
     * @return lista de adyacentes que son categoria
     */
    public ArrayList<Integer> getPageAdyacentCategories(Integer node)
    {
        ArrayList<Integer> response = new ArrayList();
        if(this.pcEdges.containsKey(node))
        {
            for(Arch arc : this.pcEdges.get(node))
            {
                int temp = arc.getDestiny();
                if(!response.contains(temp))
                {
                    response.add((Integer) temp);
                }
            }
        }
        return response;
    }

    /**
     * Obtiene las paginas que son adyacentes a una categoria concreta
     *
     * @param node categoria sobre la que buscar adyacentes
     * @return lista de adyacentes que son pagina
     */
    public ArrayList<Integer> getCategoriesAdyacentPage(Integer node)
    {
        ArrayList<Integer> response = new ArrayList();
        if(this.cpEdges.containsKey(node))
        {
            for(Arch arc : this.cpEdges.get(node))
            {
                int temp = arc.getDestiny();
                if(!response.contains(temp))
                {
                    response.add((Integer) temp);
                }
            }
        }
        return response;
    }

    /**
     * Obtiene el numero de adyacentes categoria que tiene un nodo
     *
     * @param node numero de nodo
     * @return suma de adyacentes
     */
    public int getCategoryNumberAdyacent(Integer node)
    {
        int suma = 0;
        if(this.csubcEdges.containsKey(node))
        {
            suma += this.csubcEdges.get(node).size();
        }
        if(this.csupcEdges.containsKey(node))
        {
            suma += this.csupcEdges.get(node).size();
        }
        if(this.cpEdges.containsKey(node))
        {
            suma += this.cpEdges.get(node).size();
        }
        return suma;
    }

    /**
     * Obtiene el numero de adyacentes pagina que tiene un nodo
     *
     * @param node numero de nodo
     * @return suma de adyacentes
     */
    public int getPageNumberAdyacent(Integer node)
    {
        int suma = 0;
        if(this.pcEdges.containsKey(node))
        {
            suma += this.pcEdges.get(node).size();
        }
        return suma;
    }

    /**
     * Obtiene el numero de adyacentes que son CsupC
     *
     * @param node nodo desde el que se buscan los adyacentes
     * @return numero de adyacentes
     */
    public int getCsupCAdyacent(Integer node)
    {
        if(this.csupcEdges.containsKey(node))
        {
            return this.csupcEdges.get(node).size();
        }
        return 0;
    }

    /**
     * Obtiene el numero de adyacentes que son CsubC
     *
     * @param node nodo desde el que se buscan los adyacentes
     * @return numero de adyacentes
     */
    public int getCsubCAdyacent(Integer node)
    {
        if(this.csubcEdges.containsKey(node))
        {
            return this.csubcEdges.get(node).size();
        }
        return 0;
    }

    /**
     * Obtiene la suma de adyacentes CsupC que son comunes a 2 nodos
     *
     * @param nodeA indice de categoria 1
     * @param nodeB indice de categoria 2
     * @return suma de adyacentes
     */
    public int getCsupCCommon(Integer nodeA, Integer nodeB)
    {
        int suma = 0;
        if(this.csupcEdges.containsKey(nodeA) && this.csupcEdges.containsKey(nodeB))
        {
            for(Arch ArcNodeA : this.csupcEdges.get(nodeA))
            {
                for(Arch ArcNodeB : this.csupcEdges.get(nodeB))
                {
                    if(ArcNodeA.getDestiny() == ArcNodeB.getDestiny())
                    {
                        ++suma;
                    }
                }
            }
        }
        return suma;
    }

    /**
     * Obtiene la suma de adyacentes CsubC que son comunes a 2 nodos
     *
     * @param nodeA indice de categoria 1
     * @param nodeB indice de categoria 2
     * @return suma de adyacentes
     */
    public int getCsubCCommon(Integer nodeA, Integer nodeB)
    {
        int suma = 0;
        if(this.csubcEdges.containsKey(nodeA) && this.csubcEdges.containsKey(nodeB))
        {
            for(Arch ArcNodeA : this.csubcEdges.get(nodeA))
            {
                for(Arch ArcNodeB : this.csubcEdges.get(nodeB))
                {
                    if(ArcNodeA.getDestiny() == ArcNodeB.getDestiny())
                    {
                        ++suma;
                    }
                }
            }
        }
        return suma;
    }

    /**
     * Obtiene la suma de adyacentes PC que son comunes a 2 nodos
     *
     * @param nodeA indice de pagina
     * @param nodeB indice de categoria
     * @return suma de adyacentes
     */
    public int getPCCommon(Integer nodeA, Integer nodeB)
    {
        int suma = 0;
        if(this.pcEdges.containsKey(nodeA) && this.pcEdges.containsKey(nodeB))
        {
            for(Arch ArcNodeA : this.pcEdges.get(nodeA))
            {
                for(Arch ArcNodeB : this.pcEdges.get(nodeB))
                {
                    if(ArcNodeA.getDestiny() == ArcNodeB.getDestiny())
                    {
                        ++suma;
                    }
                }
            }
        }
        return suma;
    }

    /**
     * Obtiene la suma de adyacentes CP que son comunes a 2 nodos
     *
     * @param nodeA indice de pagina
     * @param nodeB indice de categoria
     * @return suma de adyacentes
     */
    public int getCPCommon(Integer nodeA, Integer nodeB)
    {
        int suma = 0;
        if(this.cpEdges.containsKey(nodeA) && this.cpEdges.containsKey(nodeB))
        {
            for(Arch ArcNodeA : this.cpEdges.get(nodeA))
            {
                for(Arch ArcNodeB : this.cpEdges.get(nodeB))
                {
                    if(ArcNodeA.getDestiny() == ArcNodeB.getDestiny())
                    {
                        ++suma;
                    }
                }
            }
        }
        return suma;
    }

    /**
     * Cuenta las categorias en comun que tienen dos nodos
     *
     * @param nodeA primer nodo
     * @param nodeB segundo nodo
     * @return numero de categorias en comun
     */
    public int getCategoriesCommon(Integer nodeA, Integer nodeB)
    {
        int suma = 0;
        suma += this.getCsubCCommon(nodeA, nodeB);
        suma += this.getCsupCCommon(nodeA, nodeB);
        suma += this.getPCCommon(nodeA, nodeB);
        return suma;
    }

    /**
     * Cuenta las paginas en comun que tiene dos nodos
     *
     * @param nodeA primer nodo
     * @param nodeB segundo nodo
     * @return numero de paginas en comun
     */
    public int getPagesCommon(Integer nodeA, Integer nodeB)
    {
        return this.getCPCommon(nodeA, nodeB);
    }

    /**
     * Obtiene la lista de arcos que son CsubC de un nodo
     *
     * @param nodeA nodo
     * @return lista de arcos CsubC
     */
    public ArrayList<Arch> getCsubCArch(Integer nodeA)
    {
        if(this.csubcEdges.containsKey(nodeA))
        {
            return this.csubcEdges.get(nodeA);
        }
        return new ArrayList();
    }

    /**
     * Obtiene la lista de arcos que son CsupC de un nodo
     *
     * @param nodeA nodo
     * @return lista de arcos CsupC
     */
    public ArrayList<Arch> getCsupCArch(Integer nodeA)
    {
        if(this.csupcEdges.containsKey(nodeA))
        {
            return this.csupcEdges.get(nodeA);
        }
        return new ArrayList();
    }

    /**
     * Obtiene la lista de arcos que son CP de un nodo
     *
     * @param nodeA nodo
     * @return lista de arcos CP
     */
    public ArrayList<Arch> getCPArch(Integer nodeA)
    {
        if(this.cpEdges.containsKey(nodeA))
        {
            return this.cpEdges.get(nodeA);
        }
        return new ArrayList();
    }

    /**
     * Obtiene la lista de arcos que son PC de un nodo
     *
     * @param nodeA nodo
     * @return lista de arcos PC
     */
    public ArrayList<Arch> getPCArch(Integer nodeA)
    {
        if(this.pcEdges.containsKey(nodeA))
        {
            return this.pcEdges.get(nodeA);
        }
        return new ArrayList();
    }

    /**
     * Obtiene la lista de arcos que son a una categoria
     *
     * @param nodeA nodo
     * @return lista de arcos a categoria
     */
    public ArrayList<Arch> getCategoryArch(Integer nodeA)
    {
        ArrayList<Arch> response = new ArrayList();
        response.addAll(this.getCsupCArch(nodeA));
        response.addAll(this.getCsubCArch(nodeA));
        response.addAll(this.getCPArch(nodeA));
        return response;
    }

    /**
     * Obtiene la lista de arcos que son a una pagina
     *
     * @param nodeA nodo
     * @return lista de arcos a pagina
     */
    public ArrayList<Arch> getPageArch(Integer nodeA)
    {
        ArrayList<Arch> response = new ArrayList();
        response.addAll(this.getPCArch(nodeA));
        return response;
    }

    /**
     * Sobreescribe los arcos CsubC
     *
     * @param csubcEdges estructura de datos con los arcos CsubC
     */
    public void setCsubCArch(HashMap<Integer, ArrayList<Arch>> csubcEdges)
    {
        this.csubcEdges = csubcEdges;
    }

    /**
     * Sobreescribe los arcos CsupC
     *
     * @param csupcEdges estructura de datos con los arcos CsupC
     */
    public void setCsupCArch(HashMap<Integer, ArrayList<Arch>> csupcEdges)
    {
        this.csupcEdges = csupcEdges;
    }

    /**
     * Sobreescribe los arcos CP
     *
     * @param cpEdges estructura de datos con los arcos CP
     */
    public void setCPArch(HashMap<Integer, ArrayList<Arch>> cpEdges)
    {
        this.cpEdges = cpEdges;
    }

    /**
     * Sobreescribe los arcos PC
     *
     * @param pcEdges estructura de datos con los arcos PC
     */
    public void setPCArch(HashMap<Integer, ArrayList<Arch>> pcEdges)
    {
        this.pcEdges = pcEdges;
    }

    /**
     * Obtiene la lista de arcos CsubC
     *
     * @return lista de arcos
     */
    public HashMap<Integer, ArrayList<Arch>> getCsubCArch()
    {
        return this.csubcEdges;
    }

    /**
     * Obtiene la lista de arcos CsupC
     *
     * @return lista de arcos
     */
    public HashMap<Integer, ArrayList<Arch>> getCsupCArch()
    {
        return this.csupcEdges;
    }

    /**
     * Obtiene la lista de arcos CP
     *
     * @return lista de arcos
     */
    public HashMap<Integer, ArrayList<Arch>> getCPArch()
    {
        return this.cpEdges;
    }

    /**
     * Obtiene la lista de arcos PC
     *
     * @return lista de arcos
     */
    public HashMap<Integer, ArrayList<Arch>> getPCArch()
    {
        return this.pcEdges;
    }

    /**
     * Traduce una categoria a su indice correspondiente
     *
     * @param category categoria a buscar
     * @return indice o -1 si no está en el sistema
     */
    public Integer getCategoryNumber(Categoria category)
    {
        if(this.categoriaIndex.containsKey(category))
        {
            return this.categoriaIndex.get(category);
        }
        return -1;
    }

    /**
     * Traduce una pagina a su indice correspondiente
     *
     * @param page pagina a buscar
     * @return indice o -1 si no está en el sistema
     */
    public Integer getPageNumber(Pagina page)
    {
        if(this.paginaIndex.containsKey(page))
        {
            return this.paginaIndex.get(page);
        }
        return -1;
    }

    /**
     * Traduce un indice a una categoria
     *
     * @param nodeA indice de categoria
     * @return categoria asociada a ese indice
     */
    public Categoria getNumberCategory(Integer nodeA)
    {
        if(this.indexCategoria.containsKey(nodeA))
        {
            return this.indexCategoria.get(nodeA);
        }
        return null;
    }

    /**
     * Traduce un indice a una pagina
     *
     * @param nodeA indice de pagina
     * @return pagina asociada a ese indice
     */
    public Pagina getNumberPage(Integer nodeA)
    {
        if(this.indexPagina.containsKey(nodeA))
        {
            return this.indexPagina.get(nodeA);
        }
        return null;
    }

    /**
     * Traduce un indice a un nombre de categoria
     *
     * @param nodeA indice de categoria
     * @return nombre asociado a esa categoria
     */
    public String getNumberNameCategory(Integer nodeA)
    {
        Categoria category = this.getNumberCategory(nodeA);
        if(category != null)
        {
            return category.getNombre();
        }
        return "";
    }

    /**
     * Traduce un indice a un nombre de pagina
     *
     * @param nodeA indice de pagina
     * @return nombre asociado a esa pagina
     */
    public String getNumberNamePage(Integer nodeA)
    {
        Pagina page = this.getNumberPage(nodeA);
        if(page != null)
        {
            return page.getNombre();
        }
        return "";
    }

    /**
     * Una de los metodos principales de GrafoEntrada, se encarga de proveer de
     * elementos nuevos la clase a partir de cadenas de strings que provienen de
     * la interfaz de usuario o de un fichero
     *
     * @param sA nombre de nodo A
     * @param tA tipo de nodo
     * @param tArch tipo de arco
     * @param sB nombre de nodo B
     * @param tB tipo de nodo
     */
    public void setData(String sA, String tA, String tArch, String sB, String tB)
    {
        Integer na, nb;
        Arch arc = null;
        if(tA.equals("cat"))
        {
            Categoria c = new Categoria(sA);
            this.addCategoria(c);
            na = this.categoriaIndex.get(c);
        }
        else
        {
            Pagina p = new Pagina(sA);
            this.addPagina(p);
            na = this.paginaIndex.get(p);
        }
        if(tB.equals("cat"))
        {
            Categoria c = new Categoria(sB);
            this.addCategoria(c);
            nb = this.categoriaIndex.get(c);
        }
        else
        {
            Pagina p = new Pagina(sB);
            this.addPagina(p);
            nb = this.paginaIndex.get(p);
        }
        switch(tArch)
        {
            case "CsubC":
                arc = new Arch(na, nb, Arch.typeArch.CsubC);
                break;
            case "CsupC":
                arc = new Arch(na, nb, Arch.typeArch.CsupC);
                break;
            case "CP":
                arc = new Arch(na, nb, Arch.typeArch.CP);
                break;
            case "PC":
                arc = new Arch(na, nb, Arch.typeArch.PC);
                break;
        }
        this.addArch(arc);
        ++this.edgeSize;
    }

    /**
     * Permite parsear una lista de Strings al grafo de entrada
     *
     * @param list datos de entrada
     */
    public void loadFromFile(ArrayList<String> list)
    {
        if(list == null)
        {
            return;
        }
        for(String s : list)
        {
            String data[] = s.split("\\s+");
            this.setData(data[0], data[1], data[2], data[3], data[4]);
        }
    }

    /**
     * Transforma todo el contenido del grafo de entrada en una lista de strings
     *
     * @return lista de strings con la informacion del grafo de entrada
     */
    public ArrayList<String> saveToFile()
    {
        ArrayList<String> response = new ArrayList();
        Iterator<Entry<Integer, ArrayList<Arch>>> it;
        it = this.csubcEdges.entrySet().iterator();
        String A, B;
        while(it.hasNext())
        {
            Entry<Integer, ArrayList<Arch>> entry = it.next();
            ArrayList<Arch> arcs = entry.getValue();
            for(Arch arc : arcs)
            {
                A = this.getNumberNameCategory(arc.getOrigin());
                B = this.getNumberNameCategory(arc.getDestiny());
                response.add(A + "   cat   CsubC   " + B + "   cat");
            }
        }
        it = this.csupcEdges.entrySet().iterator();
        while(it.hasNext())
        {
            Entry<Integer, ArrayList<Arch>> entry = it.next();
            ArrayList<Arch> arcs = entry.getValue();
            for(Arch arc : arcs)
            {
                A = this.getNumberNameCategory(arc.getOrigin());
                B = this.getNumberNameCategory(arc.getDestiny());
                response.add(A + "   cat   CsupC   " + B + "   cat");
            }
        }
        it = this.cpEdges.entrySet().iterator();
        while(it.hasNext())
        {
            Entry<Integer, ArrayList<Arch>> entry = it.next();
            ArrayList<Arch> arcs = entry.getValue();
            for(Arch arc : arcs)
            {
                A = this.getNumberNameCategory(arc.getOrigin());
                B = this.getNumberNamePage(arc.getDestiny());
                response.add(A + "   cat   CP   " + B + "   page");
            }
        }
        it = this.pcEdges.entrySet().iterator();
        while(it.hasNext())
        {
            Entry<Integer, ArrayList<Arch>> entry = it.next();
            ArrayList<Arch> arcs = entry.getValue();
            for(Arch arc : arcs)
            {
                A = this.getNumberNamePage(arc.getOrigin());
                B = this.getNumberNameCategory(arc.getDestiny());
                response.add(A + "   page   PC   " + B + "   cat");
            }
        }
        return response;
    }

    /**
     * Se encarga de buscar todos aquellos arcos que faltan por eliminar
     *
     * @param destiny nodo destino para buscar
     * @param edges estructura de datos donde realizar la busqueda
     */
    private void removeDestiny(Integer destiny, HashMap<Integer, ArrayList<Arch>> edges)
    {
        Iterator<Entry<Integer, ArrayList<Arch>>> it = edges.entrySet().iterator();
        while(it.hasNext())
        {
            Entry<Integer, ArrayList<Arch>> entry = it.next();
            Integer origin = entry.getKey();
            ArrayList<Arch> arcs = entry.getValue();
            Iterator<Arch> at = arcs.iterator();
            while(at.hasNext())
            {
                Arch arc = at.next();
                if(arc.getDestiny() == destiny)
                {
                    at.remove();
                    --this.edgeSize;
                }
            }
            edges.put(origin, arcs);
        }
    }

    /**
     * Elimina una categoria y da la orden de eliminar los arcos asociados tanto
     * en origen como destino
     *
     * @param category categoria a eliminar
     */
    public void removeCategoria(Categoria category)
    {
        Integer numCategory = this.categoriaIndex.remove(category);
        this.indexCategoria.remove(numCategory);
        this.csubcEdges.remove(numCategory);
        this.csupcEdges.remove(numCategory);
        this.cpEdges.remove(numCategory);
        this.removeDestiny(numCategory, this.pcEdges);
        this.removeDestiny(numCategory, this.csubcEdges);
        this.removeDestiny(numCategory, this.csupcEdges);
    }

    /**
     * Elimina una pagina y da la orden de eliminar los arcos asociados tanto en
     * origen como destino
     *
     * @param page pagina a eliminar
     */
    public void removePagina(Pagina page)
    {
        Integer numPage = this.paginaIndex.remove(page);
        this.indexPagina.remove(numPage);
        this.pcEdges.remove(numPage);
        this.removeDestiny(numPage, this.cpEdges);
    }

    /**
     * Elimina un arco PC
     *
     * @param page pagina origen
     * @param category categoria destino
     */
    public void removeArchPageCategory(Integer page, Integer category)
    {
        if(this.pcEdges.containsKey(page))
        {
            ArrayList<Arch> arcs = this.pcEdges.get(page);
            Iterator<Arch> it = arcs.iterator();
            while(it.hasNext())
            {
                if(it.next().getDestiny() == category)
                {
                    it.remove();
                    --this.edgeSize;
                }
            }
        }
    }

    /**
     * Elimina un arco CP
     *
     * @param category categoria origen
     * @param page pagina destino
     */
    public void removeArchCategoryPage(Integer category, Integer page)
    {
        if(this.cpEdges.containsKey(category))
        {
            ArrayList<Arch> arcs = this.cpEdges.get(category);
            Iterator<Arch> it = arcs.iterator();
            while(it.hasNext())
            {
                if(it.next().getDestiny() == page)
                {
                    it.remove();
                    --this.edgeSize;
                }
            }
        }
    }

    /**
     * Elimina un arco CsubC
     *
     * @param categoryA categoria origen
     * @param categoryB categoria destino
     */
    public void removeArchCategorySubCategory(Integer categoryA, Integer categoryB)
    {
        if(this.csubcEdges.containsKey(categoryA))
        {
            ArrayList<Arch> arcs = this.csubcEdges.get(categoryA);
            Iterator<Arch> it = arcs.iterator();
            while(it.hasNext())
            {
                if(it.next().getDestiny() == categoryB)
                {
                    it.remove();
                    --this.edgeSize;
                }
            }
        }
    }

    /**
     * Elimina un arco CsupC
     *
     * @param categoryA categoria origen
     * @param categoryB categoria destino
     */
    public void removeArchCategorySupCategory(Integer categoryA, Integer categoryB)
    {
        if(this.csupcEdges.containsKey(categoryA))
        {
            ArrayList<Arch> arcs = this.csupcEdges.get(categoryA);
            Iterator<Arch> it = arcs.iterator();
            while(it.hasNext())
            {
                if(it.next().getDestiny() == categoryB)
                {
                    it.remove();
                    --this.edgeSize;
                }
            }
        }
    }

    /**
     * Añade un arco PC
     *
     * @param page indice de pagina
     * @param category indice de categoria
     * @param arc arco
     */
    private void addArchPageCategory(Integer page, Integer category, Arch arc)
    {
        if(this.indexPagina.containsKey(page) && this.indexCategoria.containsKey(category))
        {
            ArrayList<Arch> arcs = this.pcEdges.get(page);
            if(arcs.contains(arc))
            {
                arcs.set(arcs.indexOf(arc), arc);
            }
            else
            {
                arcs.add(arc);
            }
            this.pcEdges.put(page, arcs);
        }
    }

    /**
     * Añade un arco CP
     *
     * @param category indice de categoria
     * @param page indice de pagina
     * @param arc arco
     */
    private void addArchCategoryPage(Integer category, Integer page, Arch arc)
    {
        if(this.indexPagina.containsKey(page) && this.indexCategoria.containsKey(category))
        {
            ArrayList<Arch> arcs = this.cpEdges.get(category);
            if(arcs.contains(arc))
            {
                arcs.set(arcs.indexOf(arc), arc);
            }
            else
            {
                arcs.add(arc);
            }
            this.cpEdges.put(category, arcs);
        }
    }

    /**
     * Añade un arco CsubC
     *
     * @param categoryA indice de categoria origen
     * @param CategoryB indice de categoria destino
     * @param arc arco
     */
    private void addArchCategorySubCategory(Integer categoryA, Integer CategoryB, Arch arc)
    {
        if(this.indexCategoria.containsKey(categoryA) && this.indexCategoria.containsKey(CategoryB))
        {
            ArrayList<Arch> arcs = this.csubcEdges.get(categoryA);
            if(arcs.contains(arc))
            {
                arcs.set(arcs.indexOf(arc), arc);
            }
            else
            {
                arcs.add(arc);
            }
            this.csubcEdges.put(categoryA, arcs);
        }
    }

    /**
     * Añade un arco CsupC
     *
     * @param categoryA indice de categoria origen
     * @param CategoryB indice de categoria destino
     * @param arc arco
     */
    private void addArchCategorySupCategory(Integer categoryA, Integer CategoryB, Arch arc)
    {
        if(this.indexCategoria.containsKey(categoryA) && this.indexCategoria.containsKey(CategoryB))
        {
            ArrayList<Arch> arcs = this.csupcEdges.get(categoryA);
            if(arcs.contains(arc))
            {
                arcs.set(arcs.indexOf(arc), arc);
            }
            else
            {
                arcs.add(arc);
            }
            this.csupcEdges.put(categoryA, arcs);
        }
    }

    /**
     * Añade un arco
     *
     * @param arc arco
     */
    public void addArch(Arch arc)
    {
        Integer origin = arc.getOrigin();
        Integer destiny = arc.getDestiny();
        Arch.typeArch tipo = arc.getTypeArch();
        switch(tipo)
        {
            case CsupC:
                this.addArchCategorySupCategory(origin, destiny, arc);
                break;
            case CsubC:
                this.addArchCategorySubCategory(origin, destiny, arc);
                break;
            case CP:
                this.addArchCategoryPage(origin, destiny, arc);
                break;
            case PC:
                this.addArchPageCategory(origin, destiny, arc);
                break;
        }
    }

    /**
     * Añade una categoria
     *
     * @param category nueva categoria
     * @return true si se ha completado
     */
    public boolean addCategoria(Categoria category)
    {
        int value = this.getCategoryNumber(category);
        if(value == -1)
        {
            this.indexCategoria.put(this.categoryId, category);
            this.categoriaIndex.put(category, this.categoryId);
            this.cpEdges.put(this.categoryId, new ArrayList<Arch>());
            this.csubcEdges.put(this.categoryId, new ArrayList<Arch>());
            this.csupcEdges.put(this.categoryId, new ArrayList<Arch>());
            ++this.categoryId;
            return true;
        }
        return false;
    }

    /**
     * Añade una pagina
     *
     * @param page nueva pagina
     * @return true si se ha completado
     */
    public boolean addPagina(Pagina page)
    {
        if(this.getPageNumber(page) == -1)
        {
            this.indexPagina.put(this.pageId, page);
            this.paginaIndex.put(page, this.pageId);
            this.pcEdges.put(this.pageId, new ArrayList<Arch>());
            ++this.pageId;
            return true;
        }
        return false;
    }

    /**
     * Genera un Hash unico para este objeto
     *
     * @return
     */
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.categoryId);
        hash = 67 * hash + Objects.hashCode(this.pageId);
        hash = 67 * hash + this.edgeSize;
        hash = 67 * hash + Objects.hashCode(this.indexCategoria);
        hash = 67 * hash + Objects.hashCode(this.indexPagina);
        hash = 67 * hash + Objects.hashCode(this.categoriaIndex);
        hash = 67 * hash + Objects.hashCode(this.paginaIndex);
        hash = 67 * hash + Objects.hashCode(this.csupcEdges);
        hash = 67 * hash + Objects.hashCode(this.csubcEdges);
        hash = 67 * hash + Objects.hashCode(this.cpEdges);
        hash = 67 * hash + Objects.hashCode(this.pcEdges);
        return hash;
    }

    /**
     * Permite comparar dos Grafos de entrada
     *
     * @param obj grafo con el que comparar
     * @return
     */
    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)
        {
            return false;
        }
        if(getClass() != obj.getClass())
        {
            return false;
        }
        final GrafoEntrada other = (GrafoEntrada) obj;
        if(!Objects.equals(this.categoryId, other.categoryId))
        {
            return false;
        }
        if(!Objects.equals(this.pageId, other.pageId))
        {
            return false;
        }
        if(this.edgeSize != other.edgeSize)
        {
            return false;
        }
        if(!Objects.equals(this.indexCategoria, other.indexCategoria))
        {
            return false;
        }
        if(!Objects.equals(this.indexPagina, other.indexPagina))
        {
            return false;
        }
        if(!Objects.equals(this.categoriaIndex, other.categoriaIndex))
        {
            return false;
        }
        if(!Objects.equals(this.paginaIndex, other.paginaIndex))
        {
            return false;
        }
        if(!Objects.equals(this.csupcEdges, other.csupcEdges))
        {
            return false;
        }
        if(!Objects.equals(this.csubcEdges, other.csubcEdges))
        {
            return false;
        }
        if(!Objects.equals(this.cpEdges, other.cpEdges))
        {
            return false;
        }
        return Objects.equals(this.pcEdges, other.pcEdges);
    }

    /**
     * Permite crear una copia nueva y exacta de este grafo de entrada
     *
     * @return Grafo de entrada nuevo con los mismos datos
     */
    @Override
    public GrafoEntrada clone()
    {
        GrafoEntrada obj = null;
        try
        {
            obj = (GrafoEntrada) super.clone();
            obj.categoriaIndex = (HashMap<Categoria, Integer>) this.categoriaIndex.clone();
            obj.categoryId = this.categoryId;
            obj.cpEdges = (HashMap<Integer, ArrayList<Arch>>) this.cpEdges.clone();
            obj.csubcEdges = (HashMap<Integer, ArrayList<Arch>>) this.csubcEdges.clone();
            obj.csupcEdges = (HashMap<Integer, ArrayList<Arch>>) this.csupcEdges.clone();
            obj.edgeSize = this.edgeSize;
            obj.indexCategoria = (HashMap<Integer, Categoria>) this.indexCategoria.clone();
            obj.indexPagina = (HashMap<Integer, Pagina>) this.indexPagina.clone();
            obj.pageId = this.pageId;
            obj.paginaIndex = (HashMap<Pagina, Integer>) this.paginaIndex.clone();
            obj.pcEdges = (HashMap<Integer, ArrayList<Arch>>) this.pcEdges.clone();
        }
        catch(CloneNotSupportedException e)
        {
        }
        return obj;
    }
}