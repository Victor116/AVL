/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolbalanceado;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author ERIDE21
 */
public class ArbolBalanceado {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArbolB arbolito = new ArbolB();
        ArbolB  ar = new ArbolB();
        ar.muestraMenuAVL();
        JFrame arbolB = new JFrame("Arbol grafico");
        arbolB.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        arbolB.add(arbolito.dibujaArbol(ar));
        arbolB.setSize(600, 800);
        arbolB.setVisible(true);
        ar.preOrder(ar.raiz);
    }

}
