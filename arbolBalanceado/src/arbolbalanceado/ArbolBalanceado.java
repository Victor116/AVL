/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolbalanceado;

/**
 *
 * @author ERIDE21
 */
public class ArbolBalanceado {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArbolB  ar = new ArbolB();
        ar.insertarB(10);
        ar.insertarB(5);
        ar.insertarB(13);
        ar.insertarB(1);
        ar.insertarB(6);
        ar.insertarB(17);
        ar.insertarB(16);
        ar.preOrder(ar.raiz);
    }
    
}
