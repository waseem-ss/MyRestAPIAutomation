package org.example.collectionsdemo;

import java.util.*;

public class SetDemo {

    public static void main (String[] args){
        System.out.println("--Set Demo----");
        //Order is not preserved
        Set<String> demoHashSet = new HashSet<>();
        demoHashSet.add("one");
        demoHashSet.add("two");
        demoHashSet.add("three");
        demoHashSet.add("four");
        demoHashSet.add("Two");
        //Set will not allow duplicates
        System.out.println("demoHashSet size: "+demoHashSet.size());
        for(String element : demoHashSet)
            System.out.println("Element in demoHashSet:"+ element);


        //Order is preserved
        Set<String> demoLHashSet = new LinkedHashSet<>();
        demoLHashSet.add("one");
        demoLHashSet.add("two");
        demoLHashSet.add("three");
        demoLHashSet.add("four");


        System.out.println("demoLHashSet size: "+demoLHashSet.size());

        for(String num : demoLHashSet)
            System.out.println("Element in demoLHashSet:"+ num);

        //Tree
        Set<String> demoTHashSet = new TreeSet<>();//Sorted order
        demoTHashSet.add("one");
        demoTHashSet.add("two");
        demoTHashSet.add("three");
        demoTHashSet.add("four");
        demoTHashSet.add("five");
        demoTHashSet.add("six");
        demoTHashSet.add("Seven");

        System.out.println("demoLHashSet size: "+demoTHashSet.size());

        for(String num : demoTHashSet)
            System.out.println("Element in demoTHashSet:"+ num);

        Iterator cursor = demoTHashSet.iterator();
        System.out.println("In a while loop");
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }
}
