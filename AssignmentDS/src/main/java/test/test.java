/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import java.util.*;

/**
 *
 * @author Acer
 */
public class test {
    public static void main(String[] args) {
//        System.out.println("hhhhh");
//        System.out.println("adam");

        List<Integer> numbers = Arrays.asList(1,2,2,2,3,5);

        System.out.println(numbers);

        Set<Integer> hashSet = new LinkedHashSet<>(numbers);
        ArrayList<Integer> removedDuplicates = new ArrayList<>(hashSet);

        System.out.println(removedDuplicates);
    }

}
