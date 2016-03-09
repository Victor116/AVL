package arbolbalanceado;

import static arbolbalanceado.ArbolBalanceado.ar;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ArbolB {

    Nodo raiz;
    int cant;
    int altura;

    public ArbolB() {
        raiz = null;
    }

    public boolean insertar(int nuevo) {
        if (raiz == null)
            raiz = new Nodo(nuevo, null);
        else {
            Nodo aux = raiz;
            Nodo padre;
            while (true) {
                if (aux.dato == nuevo)
                    return false;
 
                padre = aux;

                boolean irIzq = aux.dato > nuevo;
                aux = irIzq ? aux.izq : aux.der; //condicion  ? verdadero : falso
                
                if (aux == null) {
                    if (irIzq) {
                        padre.izq = new Nodo(nuevo, padre);
                    } else {
                        padre.der = new Nodo(nuevo, padre);
                    }
                    rebalanceo(padre);
                    break;
                }
            }
        }
        return true;
    }
 
    public void eliminar(int dato) {
        if (raiz == null)
            return;
        Nodo aux = raiz;
        Nodo padre = raiz;
        Nodo delNodo = null;
        Nodo child = raiz;
 
        while (child != null) {
            padre = aux;
            aux = child;
            child = dato >= aux.dato ? aux.der : aux.izq;// condicion  ? verdadero : falso

            if (dato == aux.dato)
                delNodo = aux;
        }
 
        if (delNodo != null) {
            delNodo.dato = aux.dato;
            child = aux.izq != null ? aux.izq : aux.der;// condicion  ? verdadero : falso
 
            if (raiz.dato == dato) {
                raiz = child;
            } else {
                if (padre.izq == aux) {
                    padre.izq = child;
                } else {
                    padre.der = child;
                }
                rebalanceo(padre);
            }
        }
    }
 
    private void rebalanceo(Nodo reba) {
        recibirBalanceo(reba);
 
        if (reba.fe == -2) {
            if (obtenerPeso(reba.izq.izq) >= obtenerPeso(reba.izq.der))
                reba = rotacionIzq(reba);
            else
                reba = rotaCompID(reba);
 
        } else if (reba.fe == 2) {
            if (obtenerPeso(reba.der.der) >= obtenerPeso(reba.der.izq))
                reba = rotacionDer(reba);
            else
                reba = rotaCompDI(reba);
        }
 
        if (reba.padre != null) {
            rebalanceo(reba.padre);
        } else {
            raiz = reba;
        }
    }
    //Rotacion simple a la izquierda
    private Nodo rotacionDer(Nodo rotaIzq) {
        System.out.println("Rotacion simple a la derecha");
        Nodo aux = rotaIzq.der;
        aux.padre = rotaIzq.padre;
 
        rotaIzq.der = aux.izq;
 
        if (rotaIzq.der != null)
            rotaIzq.der.padre = rotaIzq;
 
        aux.izq = rotaIzq;
        rotaIzq.padre = aux;
 
        if (aux.padre != null) {
            if (aux.padre.der == rotaIzq) {
                aux.padre.der = aux;
            } else {
                aux.padre.izq = aux;
            }
        }
 
        recibirBalanceo(rotaIzq, aux);
 
        return aux;
    }
    //Rotacion simple a la derecha
    private Nodo rotacionIzq(Nodo rotaDer) {
         System.out.println("Rotacion simple a la izquierda");

        Nodo temp = rotaDer.izq;
        temp.padre = rotaDer.padre;
 
        rotaDer.izq = temp.der;
 
        if (rotaDer.izq != null)
            rotaDer.izq.padre = rotaDer;
 
        temp.der = rotaDer;
        rotaDer.padre = temp;
 
        if (temp.padre != null) {
            if (temp.padre.der == rotaDer) {
                temp.padre.der = temp;
            } else {
                temp.padre.izq = temp;
            }
        }
 
        recibirBalanceo(rotaDer, temp);
 
        return temp;
    }
 
    private Nodo rotaCompID(Nodo rotaCompID) {
        System.out.println("Rotacion compuesta izquierda a derecha");
        rotaCompID.izq = rotacionDer(rotaCompID.izq);
        return rotacionIzq(rotaCompID);
    }
 
    private Nodo rotaCompDI(Nodo rotaCompDI) {
        System.out.println("Rotacion compuesta derecha a izquierda");
        rotaCompDI.der = rotacionIzq(rotaCompDI.der);
        return rotacionDer(rotaCompDI);
    }
 
    private int obtenerPeso(Nodo aux) {
        if (aux == null)
            return -1;
        return 1 + Math.max(obtenerPeso(aux.der), obtenerPeso(aux.izq));
    }
 
    private void recibirBalanceo(Nodo... nodes) {
        for (Nodo aux : nodes)
            aux.fe = obtenerPeso(aux.der) - obtenerPeso(aux.izq);
    }
    
    public JPanel dibujaArbol(ArbolB ar) {
        return new Grafico(ar);
    }

    public void dibujarArbol() {
        JFrame arbolB = new JFrame("Arbol grafico");
        arbolB.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        arbolB.add(dibujaArbol(ar));
        arbolB.setSize(600, 800);
        arbolB.setVisible(true);
    }

    public void muestraMenuAVL() {
        /*
         Método encargado de desplegar el menu de opciones disponibles
         */
        System.out.println("");
        System.out.println("1.- Insertar elemento en el arbol");
        System.out.println("2.- Buscar elemento en el arbol");
        System.out.println("3- Remover elemento del arbol");
        System.out.println("4.- Recorrido PreOrder");
        System.out.println("5.- Recorrido InOrder");
        System.out.println("6.- Representacion del arbol");
        System.out.println("7.- Creditos");
        System.out.println("8.- Salir del programa");
        menuOpcionAVL();
    }

    public void menuOpcionAVL() {
        /*
         Método encargado de optener la opción del usuario
         y ejecutar posteriormente la instruccion ordenada
         */

        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        try {
            opcion = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Opcion invalida, introduzca solo opciones aceptables");
            muestraMenuAVL();
        }

        int info;
        boolean resultado;
        switch (opcion) {
            case 1:
                info = consigueValor();
                insertar(info);
                muestraMenuAVL();
                break;
            case 2:
                try {
                    info = consigueValor();
                    resultado = buscaElemento(info);
                    if (resultado) {
                        System.out.println("El elemento se encuentra en el arbol");
                    } else {
                        System.out.println("El elemento no se encuentra en el arbol");
                    }
                    muestraMenuAVL();
                } catch (Exception e) {

                }
                break;
            case 3:
                info = consigueValor();
                eliminar(info);
                muestraMenuAVL();
                break;
            case 4:
                preOrder(raiz);
                System.out.println();
                muestraMenuAVL();
                break;
            case 5:
                inOrder(raiz);
                System.out.println();
                muestraMenuAVL();
                break;
            case 6:
                dibujarArbol();
                muestraMenuAVL();
                break;
            case 7:
                System.out.println("Universidad Politecnica de Chiapas");
                System.out.println("Materia: Estructuras de Datos Avanzadas");
                System.out.println("Catedratico: Mtra. Aremy Olaya Virrueta Gordillo");
                System.out.println("Alumno: Edgardo Rito Deheza Matricula: 143370");
                System.out.println("Alumno: Ernesto Sandoval Becerra Matricula: 143374");
                System.out.println("Alumno: Diana Alondra Toledo Maza: 143355");
                System.out.println("Alumno: Freddy Alesandro Gamboa: 133355");
                System.out.println("Alumno: Carlos Alejandro Zenteno Robles: 143382");
                muestraMenuAVL();
                break;
            case 8:
                break;
            default:
                System.out.println("Opcion invalida");
                menuOpcionAVL();
                break;
        }

    }

    public int consigueValor() {

             //Método encargado de verificar que los valores que introduce el usuario
        //sean valores aceptables
        System.out.print("\nvalor: ");
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        try {
            opcion = sc.nextInt();
            return opcion;
        } catch (Exception e) {
            System.out.println("Opcion invalida, introduzca solo valores enteros");
            return consigueValor();
        }

    }

    public void preOrder(Nodo reco) {

          //Método para recorrer el árbol en el sentido preOrder(raíz, izquierda, derecha)
        if (reco != null) {
            System.out.print(reco.dato + " ");
            preOrder(reco.izq);
            preOrder(reco.der);
        }
    }

    public void inOrder(Nodo reco) {

          //Método para recorrer el árbol en el sentido inOrder(izquierda, raíz, derecha)
        if (reco != null) {
            inOrder(reco.izq);
            System.out.print(reco.dato + " ");
            inOrder(reco.der);
        }
    }

    public boolean buscaElemento(int info) {

        //Método encargado de proporcionar la información del nodo solicitado(nivel del nodo, valor de sus nodos
        //, tipo de nodo(rama, hoja)
        //)
        
      	Nodo anterior = null, reco;
        int nivel = 0;
        reco = raiz;
        while (reco != null) {
            nivel++;
            anterior = reco;
            if (info == reco.dato) {
                  	//System.out.println("Nivel en el que se encuentra el nodo: "+(nivel-1));
                //System.out.print("valor izquierdo: \aux");
                if (reco.izq != null) //System.out.println(reco.izq.getDato());
                //System.out.print("valor derecho: \aux");
                {
                    if (reco.der != null) //System.out.println(reco.der.getDato());
                    {
                        if (reco.der == null && reco.izq == null) //System.out.println("nodo de tipo: hoja");
                        //else
                        //System.out.println("Nodo de tipo: rama");
                        //System.out.println("Altura del nodo: "+retornarAltura(reco));
                        {
                            return true;
                        }
                    }
                }
            }
            if (info < reco.dato) {
                reco = reco.izq;
            } else {
                reco = reco.der;
            }

        }
        return false;
    }
    /*
     //Metodo para insertar AVL
    public Nodo insertarAVL(Nodo nuevo, Nodo subAr) {
        Nodo nuevoPadre = subAr;
        if (nuevo.dato < subAr.dato) {
            if (subAr.izq == null) {
                subAr.izq = nuevo;
            } else {
                subAr.izq = insertarAVL(nuevo, subAr.izq);
                actualizarAltura(subAr);
                if (obtenerFE(subAr.izq) - obtenerFE(subAr.der) == 2) {
                    if (nuevo.dato < subAr.izq.dato) {
                        nuevoPadre = rotacionIzq(subAr);
                    } else {
                        nuevoPadre = rotacionCompIzq(subAr);
                    }
                }
            }
        } else if (nuevo.dato > subAr.dato) {
            if (subAr.der == null) {
                subAr.der = nuevo;
            } else {
                subAr.der = insertarAVL(nuevo, subAr.der);
                actualizarAltura(subAr);
                if ((obtenerFE(subAr.der) - obtenerFE(subAr.izq) == 2)) {
                    if (nuevo.dato > subAr.der.dato) {
                        nuevoPadre = rotacionIzq(subAr);
                    } else {
                        nuevoPadre = rotacionCompDer(subAr);
                    }
                }
            }
        } else {
            System.out.println("Nodo duplicado");
        }
        //Actualizar el FE
        actualizarAltura(subAr);
        return nuevoPadre;
    }
    //Metodo para obtener el Factor de Equilibrio
    public int obtenerFE(Nodo reco) {
        if (reco == null) {
            return -1;
        } else {
            return reco.peso;
        }
    }

    //Rotacion Simple a la Izquierda
    public Nodo rotacionIzq(Nodo nodort) {
        System.out.println("Usando rotacion Simple a la izquierda");
        Nodo aux = nodort.izq;
        nodort.izq = aux.der;
        aux.der = nodort;
        nodort.peso = max(obtenerFE(nodort.izq), obtenerFE(nodort.der)) + 1;
        aux.peso = max(obtenerFE(aux.izq), obtenerFE(aux.der)) + 1;
        return aux;
    }

    //Rotacion Simple a la Derecha
    public Nodo rotacionIzq(Nodo nodort) {
        System.out.println("Usando rotacion simple a la derecha");
        Nodo aux = nodort.der;
        nodort.der = aux.izq;
        aux.izq = nodort;
        nodort.peso = max(obtenerFE(nodort.izq), obtenerFE(nodort.der)) + 1;
        aux.peso = max(obtenerFE(aux.izq), obtenerFE(aux.der)) + 1;
        return aux;
    }

    //Rotacion Compuesta a la Izquierda
    public Nodo rotacionCompIzq(Nodo nodoCom) {
        System.out.println("Usando rotacion Compuesta a la izquierda");
        Nodo aux;
        nodoCom.izq = rotacionIzq(nodoCom.izq);
        aux = rotacionIzq(nodoCom);
        return aux;
    }

    //Rotacion Compuesta a la Derecha
    public Nodo rotacionCompDer(Nodo nodoCom) {
        System.out.println("Usando rotacion Compuesta a la derecha");
        Nodo aux;
        nodoCom.der = rotacionIzq(nodoCom.der);
        aux = rotacionIzq(nodoCom);
        return aux;
    }
    //Metodo para insertar Balanceadamente

    public void insertarB(int info) {
        Nodo nuevo = new Nodo(info);
        if (raiz == null) {
            raiz = nuevo;
        } else {
            raiz = insertarAVL(nuevo, raiz);
        }
    }

    //Actualizar el FE
    public void actualizarAltura(Nodo subAr) {
        if ((subAr.izq == null) && (subAr.der != null)) {
            subAr.peso = subAr.der.peso + 1;
        } else if ((subAr.der == null) && (subAr.izq != null)) {
            subAr.peso = subAr.izq.peso + 1;
        } else {
            subAr.peso = max(obtenerFE(subAr.izq), obtenerFE(subAr.der)) + 1;
        }
    }

    public int max(int a, int b) {
        if (a >= b) {
            return a;
        }
        return b;
    }
    
    public void verificaNodo(Nodo nodo, Nodo reco, int info) {

        
         Método encargado de identificar el tipo de nodo que procedera a ser eliminado
         Nodo:
         -reco=nodo a eliminar
         -nodo = nodo padre de reco
         -info = dato que se quiere eliminar
         
        if (nodo == null) {//Si lo que desea es eliminarse la raiz

            if (reco.der == null && reco.izq == null) {//aqui solo entra hojas
                raiz = null;
                return;
            }
            if ((reco.izq != null && reco.der == null) || (reco.der != null && reco.izq == null)) {//aqui solo entra raiz con un hijo
                System.out.println("Elimina raiz con un solo hijo");
                if (reco.izq != null) {
                    raiz = reco.izq;
                }
                if (reco.der != null) {
                    raiz = reco.der;
                }
                //               balancear(raiz);
                actualizarAltura(raiz);
                return;
            }

            if (reco.der != null && reco.izq != null) {//raiz con dos hijos
                System.out.println("Elimina raiz con dos hijos");
                Nodo aux = null;
                aux = reco.der;
                Nodo last = aux;
                System.out.println("Valor de aux: " + aux.dato);
                if (raiz.der.der == null && raiz.der.izq == null && raiz.izq.der == null && raiz.izq.izq == null) {
                    raiz.dato = raiz.der.dato;
                    raiz.der = null;
                    return;
                }
                while (aux.izq != null) {
                    last = aux;
                    aux = aux.izq;
                    if (aux.der == null && aux.izq == null) {
                        break;
                    }
                }
                System.out.println("Valor de aux: " + aux.dato);
                raiz.dato = aux.dato;
                last.izq = null;
                //raiz = reco.der;
                //balancear(raiz);
                actualizarAltura(raiz);
            }

            System.out.println("\nLa raiz se elimino correctamente\aux");
            return;
        }

        if (reco.der == null && reco.izq == null) {//verifica que si lo que se va a eliminar es una hoja
            if (info < nodo.dato) {
                nodo.izq = null;
            } else {
                nodo.der = null;
            }

            if (reco.der == null && reco.izq == null) {
                System.out.println("\nEl nodo se elimino correctamente\aux");
            }
            actualizarAltura(nodo);
            //balancear(nodo);

        }

        if (reco.der != null && reco.izq != null) {//verifica si el nodo a eliminar tiene dos hijos

            
             Por convencion tomare el mayor de lado izquierdo
             nodo = padre de reco
             reco = nodo a eliminar
             
            System.out.println("\nEliminare un nodo con dos hijos\aux");
            Nodo aux = null;
            aux = reco.der;
            while (aux.izq != null) {
                aux = aux.der;
                if (aux.der != null && aux.izq != null) {

                    break;
                }
            }
            aux.izq = reco.izq;

            if (info > nodo.dato) {
                nodo.der = reco.der;
            } else {
                nodo.izq = reco.der;
            }
            actualizarAltura(nodo);
            Nodo nuevoPadre = nodo;
            if (obtenerFE(nodo.izq) - obtenerFE(nodo.der) == 2) {
                if (nodo.dato < nodo.izq.dato) {
                    nuevoPadre = rotacionIzq(nodo);
                } else {
                    nuevoPadre = rotacionCompIzq(nodo);
                }
            }
            if ((obtenerFE(nodo.der) - obtenerFE(nodo.izq) == 2)) {
                if (nodo.dato > nodo.der.dato) {
                    nuevoPadre = rotacionIzq(nodo);
                } else {
                    nuevoPadre = rotacionCompDer(nodo);
                }
            }
        }

        if (reco.der == null || reco.izq == null) {// verifica si el nodo a eliminar tiene un solo hijo
            boolean lado;

            if (reco.dato > nodo.dato) {
                lado = true;
            } else {
                lado = false;
            }

            if (reco.der != null) {

                if (lado) {
                    nodo.der = reco.der;
                    actualizarAltura(nodo.der);
                    //balancear(nodo.der);
                } else {
                    nodo.izq = reco.der;
                    actualizarAltura(nodo.izq);
                    //balancear(nodo.izq);
                }

                System.out.println("\nEl nodo se elimino correctamente\aux");
                return;
            }
            if (reco.izq != null) {
                if (lado) {
                    nodo.der = reco.izq;
                    actualizarAltura(nodo.der);
                    //balancear(nodo.der);
                } else {
                    nodo.izq = reco.izq;
                    actualizarAltura(nodo.izq);
                    //balancear(nodo.izq);
                }
                System.out.println("\nEl nodo se elimino correctamente\aux");
            //actualizarAltura(nodo);
                //  balancear(nodo);
                return;
            }

        }

        //actualizarAltura(raiz);
        //balancear(raiz);
    }
    
     public void muestraMenu(){

     Método encargado de desplegar el menu de opciones disponibles
     System.out.println("1.- Insertar elemento en el arbol (manual)");
     System.out.println("2.- Insertar elemento mediante archivo");
     System.out.println("3.- Buscar elemento en el arbol");
     System.out.println("4.- Remover elemento del arbol");
     System.out.println("5.- Info del arbol");
     System.out.println("6.- Recorrido PreOrder");
     System.out.println("7.- Recorrido InOrder");
     System.out.println("8.- Recorrido PostOrder");
     System.out.println("9.- Creditos");
     System.out.println("10.- Salir del programa");
     menuOpcion();
     }

     public void menuOpcion(){
    
     Método encargado de optener la opción del usuario
     y ejecutar posteriormente la instruccion ordenada
   

     Scanner sc = new Scanner(System.in);
     int opcion=0;
     try{
     opcion = sc.nextInt();
     }catch(Exception e){
     System.out.println("Opcion invalida, introduzca solo opciones aceptables");
     muestraMenu();
     }

     int info;
     boolean resultado;
     switch(opcion){
     case 1:
     info = consigueValor();
     insertar(info);
     muestraMenu();
     break;
     case 2:
     try{
     introduceArchivo();
     muestraMenu();
     }catch(Exception e){

     }
     break;
     case 3:
     info = consigueValor();
     resultado = buscaElemento(info);
     if(resultado)
     System.out.println("El elemento se encuentra en el arbol");
     else
     System.out.println("El elemento no se encuentra en el arbol");
     muestraMenu();
     break;
     case 4:
     info = consigueValor();
     eliminaNodo(info);
     muestraMenu();
     break;
     case 5:
     if(raiz !=null){//verifica la existencia de un arbol
     System.out.println("El arbol no esta vacio");
     System.out.println("Valor de la raiz: "+raiz.dato);
     }
     else
     System.out.println("El arbol esta vacio");

     System.out.println("Cantidad de nodos del arbol: "+cantidad());
     System.out.println("Altura del arbol: "+retornarAltura(raiz));
     muestraMenu();
     break;
     case 6:
     preOrder(raiz);
     System.out.println();
     muestraMenu();
     break;
     case 7:
     inOrder(raiz);
     System.out.println();
     muestraMenu();
     break;
     case 8:
     postOrder(raiz);
     System.out.println();
     muestraMenu();
     break;
     case 9:
     System.out.println("Universidad Politecnica de Chiapas");
     System.out.println("Materia: Estructuras de Datos Avanzadas");
     System.out.println("Catedratico: Mtra. Aremy Olaya Virrueta Gordillo");
     System.out.println("Alumno: Ernesto Sandoval Becerra");
     System.out.println("Matricula: 143374");
     muestraMenu();
     break;
     case 10:
     System.exit(0);
     break;
     default:
     System.out.println("Opcion invalida");
     muestraMenu();
     break;
     }

     }

     public void introduceArchivo() throws FileNotFoundException, IOException{

    
     Método encargado de leer un archivo para extraer los datos del mismo
     y asignarlos al arbol
    
     Scanner sc = new Scanner(System.in);
     String[] allData;
     int[] allInt;
     String data="", line;
     FileReader fr;
     BufferedReader br;
     System.out.println("Ingrese el nombre o ruta del archivo: ");
     String file = sc.nextLine();

     fr = new FileReader(file);
     br = new BufferedReader(fr);

     while ((line = br.readLine()) != null){
     data += line + ",";
     }

     allData = data.split(",");
     allInt = new int[allData.length];
     int a = 0;
     for (String sw: allData) {
     allInt[a] = Integer.parseInt(sw);
     a++;
     }

     for (int x=0;x<allInt.length ;x++ ) {
     insertar(allInt[x]);
     }
     }

     public void insertar(int info){

    
     Método encargado de insertar valores en el árbol
     Nodo:
     -nuevo: nodo utilizado para almacenar el nuevo valor que formara parte del árbol
     -anterior: funciona como noda auxiliar encargado de almacenar el ultimo valor que fue recorrido (padre de reco)
     -reco: nodo utilizado para recorrer el árbol hasta encontrar un posición vacía para insertar el nodo nuevo
    

     Nodo nuevo;
     nuevo = new Nodo ();
     nuevo.dato = info;
     nuevo.izq = null;
     nuevo.der = null;
     if (raiz == null){
     raiz = nuevo;

     }else{
     Nodo anterior = null, reco;  //reco se utiliza en todo el programa para recorrer el arbol
     reco = raiz;
     while (reco != null)     // hasta encontrar un lugar vacio, respetando el orden
     {
     anterior = reco;
     if(info == reco.dato)//Si es un dato repetido no lo inserta
     return;
     if (info < reco.dato){
     reco = reco.izq;
     }
     else{
     reco = reco.der;
     }

     }
     if (info < anterior.dato)     // de acuerdo a si es mayor o menor se coloca a la izq o der
     anterior.izq = nuevo;
     else
     anterior.der = nuevo;
     }

     System.out.println("valor se inserto correctamente");
     }

     public void preOrder (Nodo reco)
     {
        
     Método para recorrer el árbol en el sentido preOrder(raíz, izquierda, derecha)
        
     if (reco != null)
     {
     System.out.print(reco.dato + " ");
     preOrder (reco.izq);
     preOrder (reco.der);
     }
     }

     public void inOrder (Nodo reco)
     {
        
     Método para recorrer el árbol en el sentido inOrder(izquierda, raíz, derecha)
        
     if (reco != null)
     {
     inOrder (reco.izq);
     System.out.print(reco.dato + " ");
     inOrder (reco.der);
     }
     }

     public void postOrder (Nodo reco)
     {
       
     Método para recorrer el árbol en el sentido postOrder(izquierda, derecha, raíz)
        
     if (reco != null)
     {
     postOrder (reco.izq);
     postOrder (reco.der);
     System.out.print(reco.dato + " ");
     }
     }

     public boolean buscaElemento(int info){
        
     Método encargado de proporcionar la información del nodo solicitado
     (nivel del nodo, valor de sus nodos, tipo de nodo(rama, hoja))
        
     Nodo anterior = null, reco;
     int nivel=0;
     reco = raiz;
     while (reco != null)
     {
     nivel++;
     anterior = reco;
     if(info == reco.dato){
     //System.out.println("Nivel en el que se encuentra el nodo: "+(nivel-1));
     //System.out.print("valor izquierdo: \aux");
     if(reco.izq != null)
     //System.out.println(reco.izq.getDato());
     //System.out.print("valor derecho: \aux");
     if(reco.der != null)
     //System.out.println(reco.der.getDato());
     if(reco.der==null && reco.izq==null)
     //System.out.println("nodo de tipo: hoja");
     //else
     //System.out.println("Nodo de tipo: rama");
     //System.out.println("Altura del nodo: "+retornarAltura(reco));

     return true;
     }
     if (info < reco.dato){
     reco = reco.izq;
     }
     else{
     reco = reco.der;
     }

     }
     return false;
     }

     public void eliminaNodo(int info){

        
     Método encargado de buscar el nodo a eliminar y posteriormente
     enviarlo al método verificaNodo() para clasificarlo y eliminarlo
     adecuadamente
       
     Nodo anterior = null, reco;  //reco se utiliza en toso el programa para recorrer el arbol
     reco = raiz;
     int lado;
     while (reco != null)     // hasta encontrar un lugar vacio, respetando el orden
     {

     if(reco.dato==info)
     {
     verificaNodo(anterior,reco,info);
     return;
     }
     anterior = reco;
     //System.out.println("pase por aqui");
     if (info < reco.dato){
     reco = reco.izq;
     }
     else{
     reco = reco.der;
     }


     }
     System.out.println("El elemento que desea eliminar no se encuentra en el arbol");

     }

     public int cantidad() {
        
     Devuelve la cantidad de nodos que se encuentran en el árbol actualmente
        
     cant = 0;
     cantidad(raiz);
     return cant;
     }

     private void cantidad(Nodo reco) {
     if (reco != null) {
     cant++;
     cantidad(reco.getIzq());
     cantidad(reco.getDer());
     }
     }

     public int retornarAltura(Nodo nodo) {
      
     Método encargado de retornar la altura actual del árbol
      
     altura = 0;
     retornarAltura(nodo, 1);
     return altura-1;
     }

     private void retornarAltura(Nodo reco, int nivel) {
     if (reco != null) {
     retornarAltura(reco.getIzq(), nivel + 1);
     if (nivel > altura) {
     altura = nivel;
     }
     retornarAltura(reco.getDer(), nivel + 1);
     }
     }
     */
}
