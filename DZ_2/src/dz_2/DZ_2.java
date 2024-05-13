/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dz_2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vladyslav Dzyhovskyi
 */
public class DZ_2 {

    public static void main(String[] args) {
        Path file = Paths.get("input.txt");
        byte[] encoded;
        try {
            encoded = Files.readAllBytes(file);
            String s = new String(encoded, StandardCharsets.US_ASCII);
            String[] splited = s.split("\n");
            firstS(splited[0].split(","));
            secondS(splited[1].split(","));
            thirdS(splited[2].split(","));
        } catch (IOException ex) {
            Logger.getLogger(DZ_2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void firstS(String[] words) {
        System.out.println("Global Positioning System Fix Data");
        System.out.println(words[1] + " час фіксації, ччммсс.ссс");
        System.out.println(words[2] + " широта, " + words[2].substring(0, 2) 
                + " градусів, " + words[2].substring(2) + " хвилин");
        System.out.println(words[3] + " північна");
        System.out.println(words[4] + " довгота, " + words[4].substring(0, 2) 
                + " градусів, " + words[4].substring(2) + " хвилин");
        System.out.println(words[5] + " східна");
        System.out.println(words[6] + " якість фіксації");
        System.out.println(words[7] + " кількість супутників");
        System.out.println(words[8] + " фактор погіршення точності");
        System.out.println(words[9] + " висота над рівнем моря");
        System.out.println(words[10] + " одиниці вимірювання висоти");
        System.out.println(words[11] + " різниця між еліпсоїдом землі і рівнем моря");
        System.out.println(words[12] + " одиниці вимірювання висоти");
        System.out.println(words[13] + " час останнього оновлення DGPS");
        System.out.println(words[14].substring(0, 4) + " ідентифікатор станції DGPS");
        System.out.println(words[14].substring(4, 7) + " контрольна сума");     
    }
    
    private static void secondS(String[] words) {
        System.out.println("\n\nРекомендований мінімум навігаційних даних");
        System.out.println(words[1] + " час фіксації, ччммсс.ссс");
        System.out.println(words[2] + " ознака достовірності даних");
        System.out.println(words[3] + " широта, " + words[3].substring(0, 2) 
                + " градусів, " + words[3].substring(2) + " хвилин");
        System.out.println(words[4] + " північна");
        System.out.println(words[5] + " довгота, " + words[5].substring(0, 2) 
                + " градусів, " + words[5].substring(2) + " хвилин");
        System.out.println(words[6] + " східна");
        System.out.println(words[7] + " швидкість у вузлах");
        System.out.println(words[8] + " істинний курс");
        System.out.println(words[9] + " дата фіксації, ччммрр");
        System.out.println(words[10] + " магнітне відмінювання");
        System.out.println(words[11] + " східне");
        System.out.println(words[12].substring(0, 1) + " спосіб обчислення координат");
        System.out.println(words[12].substring(1, 4) + " контрольна сума");     
    }
        
    private static void thirdS(String[] words) {
        System.out.println("\n\nTrack Made Good and Ground Speed");
        System.out.println(words[1] + " істинний курс");
        System.out.println(words[2] + " ознака достовірності курсу");
        System.out.println(words[3] + " магнітне відмінювання");
        System.out.println(words[4] + " магнитний");
        System.out.println(words[5] + " швидкість");
        System.out.println(words[6] + " одиниці вимірювання швидкості (вузли)");
        System.out.println(words[7] + " швидкість");
        System.out.println(words[8].substring(0, 1) + " одиниці вимірювання швидкості (км/ч)");
        System.out.println(words[8].substring(1, 4) + " контрольна сума");
    }
    
}
