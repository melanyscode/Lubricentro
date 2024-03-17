/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import javax.swing.JOptionPane;

/**
 *
 * @author Melanie Gutierrez
 */
public class Menu {
    public static int Menu(String titulo, String mensaje, String[] opcs, String defaultValue) {
        int opc = JOptionPane.showOptionDialog(null, mensaje, titulo, 0, JOptionPane.QUESTION_MESSAGE, null, opcs, defaultValue);
        return opc;
    }
}
