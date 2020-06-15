package com.chaoliu.juc;


//判断单向链表有环
public class LinkedCycile {

    boolean isCycle(Node head) {
        if (head == null) return false;
        Node node = head;
        int count = 0;
        while (node != null) {
            if (node.count == -1) {
                node.count = count;
            }
            if (node.count != -1 && node.count <= count) {
                return false;
            }
            count++;
            node = node.next;
        }
        return true;
    }

    boolean isCycleV2(Node head) {
        if (head == null) return false;
        Node node = head;
        Node nodeStep2;
        while (true) {
            node = node.next;
            if (node == null) return false;
            nodeStep2 = node.next;
            if (nodeStep2 == null) return false;
            if (node == nodeStep2) return true;
        }
    }

    private class Node {
        Node next;
        int count = -1;//计数器
    }

}