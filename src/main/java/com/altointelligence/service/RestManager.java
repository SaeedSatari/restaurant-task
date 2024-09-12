package com.altointelligence.service;

import com.altointelligence.model.ClientsGroup;
import com.altointelligence.model.Table;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RestManager {

    private final Map<Integer, List<Table>> tableMap;
    private final Queue<ClientsGroup> waitingQueue;
    private final Map<ClientsGroup, Table> seatingMap;

    public RestManager(List<Table> tables) {
        this.tableMap = new HashMap<>();
        this.waitingQueue = new PriorityQueue<>(Comparator.comparingInt(g -> g.size));
        this.seatingMap = new HashMap<>();

        IntStream.rangeClosed(2, 6)
                .forEach(size -> tableMap.put(size, new ArrayList<>()));

        tables.forEach(table -> tableMap.get(table.size).add(table));
    }

    public void onArrive(ClientsGroup group) {
        if (!seatGroup(group)) {
            waitingQueue.offer(group);
        }
    }

    public void onLeave(ClientsGroup group) {
        Table table = seatingMap.remove(group);
        if (table != null) {
            table.removeGroup(group);
            processQueue();
        } else {
            waitingQueue.remove(group);
        }
    }

    public Table lookup(ClientsGroup group) {
        return seatingMap.get(group);
    }

    private boolean seatGroup(ClientsGroup group) {
        for (int size = group.size; size <= 6; size++) {
            List<Table> tables = tableMap.get(size);
            if (tables != null) {
                for (Table table : tables) {
                    if (table.isFullyAvailable() || table.canSeat(group)) {
                        table.seatGroup(group);
                        seatingMap.put(group, table);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void processQueue() {
        List<ClientsGroup> toRemove = waitingQueue.stream()
                .filter(this::seatGroup)
                .collect(Collectors.toList());
        waitingQueue.removeAll(toRemove);
    }
}