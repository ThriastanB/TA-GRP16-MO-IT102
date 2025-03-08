package com.motorph.inventory;

public class InventoryBST {
    private class Node {
        InventoryData data;
        Node left, right;

        Node(InventoryData data) {
            this.data = data;
            left = right = null;
        }
    }

    private Node root;

    public void insert(InventoryData data) {
        root = insertRec(root, data);
    }

    private Node insertRec(Node root, InventoryData data) {
        if (root == null) {
            return new Node(data);
        }
        if (data.getEngineNumber().compareTo(root.data.getEngineNumber()) < 0) {
            root.left = insertRec(root.left, data);
        } else if (data.getEngineNumber().compareTo(root.data.getEngineNumber()) > 0) {
            root.right = insertRec(root.right, data);
        }
        return root;
    }

    public InventoryData search(String engineNumber) {
        Node node = searchRec(root, engineNumber);
        return (node != null) ? node.data : null;
    }

    private Node searchRec(Node root, String engineNumber) {
        if (root == null || root.data.getEngineNumber().equals(engineNumber)) {
            return root;
        }
        if (engineNumber.compareTo(root.data.getEngineNumber()) < 0) {
            return searchRec(root.left, engineNumber);
        }
        return searchRec(root.right, engineNumber);
    }

    public void delete(String engineNumber) {
        root = deleteRec(root, engineNumber);
    }

    private Node deleteRec(Node root, String engineNumber) {
        if (root == null) {
            return root;
        }
        if (engineNumber.compareTo(root.data.getEngineNumber()) < 0) {
            root.left = deleteRec(root.left, engineNumber);
        } else if (engineNumber.compareTo(root.data.getEngineNumber()) > 0) {
            root.right = deleteRec(root.right, engineNumber);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            root.data = minValue(root.right);
            root.right = deleteRec(root.right, root.data.getEngineNumber());
        }
        return root;
    }

    private InventoryData minValue(Node root) {
        InventoryData minv = root.data;
        while (root.left != null) {
            minv = root.left.data;
            root = root.left;
        }
        return minv;
    }

    public void inOrder() {
        inOrderRec(root);
    }

    private void inOrderRec(Node root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.println(root.data);
            inOrderRec(root.right);
        }
    }
}