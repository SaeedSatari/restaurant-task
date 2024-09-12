package com.altointelligence;

import com.altointelligence.model.ClientsGroup;
import com.altointelligence.model.Table;
import com.altointelligence.service.RestManager;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        List<Table> tables = new ArrayList<>();
        tables.add(new Table(2));
        tables.add(new Table(3));
        tables.add(new Table(4));
        tables.add(new Table(5));
        tables.add(new Table(6));

        RestManager manager = new RestManager(tables);

        System.out.println("-------------------------------------------");
        System.out.println("Test case 1: Group of 1 arrives and should sit at the smallest table (2 seats)");
        ClientsGroup group1 = new ClientsGroup(1);
        manager.onArrive(group1);
        printSeatingInfo(manager, group1, 1);

        System.out.println("Test case 2: Group of 2 arrives and should sit at the next available table (3-seat table)");
        ClientsGroup group2 = new ClientsGroup(2);
        manager.onArrive(group2);
        printSeatingInfo(manager, group2, 2);

        System.out.println("Test case 3: Group of 3 arrives, should be seated at the 4-seat table");
        ClientsGroup group3 = new ClientsGroup(3);
        manager.onArrive(group3);
        printSeatingInfo(manager, group3, 3);

        System.out.println("Test case 4: Group of 4 arrives, should be seated at the 5-seat table");
        ClientsGroup group4 = new ClientsGroup(4);
        manager.onArrive(group4);
        printSeatingInfo(manager, group4, 4);

        System.out.println("Test case 5: Group of 5 arrives, should be seated at the 6-seat table");
        ClientsGroup group5 = new ClientsGroup(5);
        manager.onArrive(group5);
        printSeatingInfo(manager, group5, 5);

        System.out.println("Test case 6: Group of 6 arrives, but no table is available, should wait");
        ClientsGroup group6 = new ClientsGroup(6);
        manager.onArrive(group6);
        printSeatingInfo(manager, group6, 6);

        System.out.println("Test case 7: Check that the queue handles group 6 when group 1 leaves");
        manager.onLeave(group1);
        System.out.println("Group 1 left. Let's check if Group 6 can now be seated.");
        printSeatingInfo(manager, group6, 6);

        System.out.println("Test case 8: Group of 1 arrives again, should be seated at the 2-seat table");
        ClientsGroup group7 = new ClientsGroup(1);
        manager.onArrive(group7);
        printSeatingInfo(manager, group7, 7);

        System.out.println("Test case 9: Group 6 gets seated when a larger table is freed");
        manager.onLeave(group5);
        System.out.println("Group 5 left. Now group 6 should be seated.");
        printSeatingInfo(manager, group6, 6);
    }

    private static void printSeatingInfo(RestManager manager, ClientsGroup group, int groupNumber) {
        Table table = manager.lookup(group);
        if (table != null) {
            System.out.println("Group " + groupNumber + " seated at table: " + table.size + "-seat table");
            System.out.println("-------------------------------------------");
        } else {
            System.out.println("Group " + groupNumber + " is still waiting for a table.");
            System.out.println("-------------------------------------------");
        }
    }
}