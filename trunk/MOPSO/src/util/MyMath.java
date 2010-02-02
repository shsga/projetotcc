/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Elliackin
 */
public class MyMath {

    public static double[] convertBase(int d,int base,int wide){
        double[] value = new double[wide];
        ArrayList result = new ArrayList();
        int i =0;
        while(d != 0){
            result.add(i, d % base);
            d = d/base;
        }
        int size = result.size();
        for(i =0; i < size; i++){
            value[i] = ((Integer)result.get(size-1-i)).intValue();
        }
        return value;
    }
}
