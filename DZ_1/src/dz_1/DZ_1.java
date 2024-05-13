/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | ddddddddd
 * and open the template in the editor.
 */
package dz_1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DZ_1 {

public static byte Reverse(byte b) {
    byte result = 0;
    for(int i = 0; i <8; i++) {
        result <<= 1;
        result |= b &1;
        b >>= 1;
    }
    return result;
}

public static void main(String[] args) {
    Path filein = Paths.get("25.in");
    Path fileout = Paths.get("25.out");
    byte[] arrayin;
    byte[] arrayout;

    try{
        arrayin = Files.readAllBytes(filein);
        arrayout = new byte[arrayin.length];
        for(int i = 0; i <arrayin.length; i++) {
            if(i % 4 == 0) {
                arrayout[i] = Reverse(arrayin[i]);
            } else{
                arrayout[i] = arrayin[i];
            }
        }
        for(int i = 0; i <arrayin.length; i++) {
            System.out.println(arrayin[i] + "       " + arrayout[i]);
        }
        Files.write(fileout, arrayout);
    } catch(IOException e) {
            System.out.println(e);
    }
}}
