package org.example.collectionsdemo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListDemo {

    public static void main (String[] args){
        System.out.println("--List Demos--");
        List arrayList = new ArrayList();
        arrayList.add(23);
        arrayList.add("twenty");
        arrayList.add(2,45);
        arrayList.add(0,15);
        arrayList.add(2,10);
        arrayList.add(2,45);

        System.out.println(arrayList);
        arrayList.remove("twenty");
        System.out.println("After deletion " + arrayList);
        Set demoSet = new HashSet();
        demoSet.add(arrayList);
        System.out.println(demoSet);

    }
}
