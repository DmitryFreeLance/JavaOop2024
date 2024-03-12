package ru.academits.shagaev.tree;

import ru.academits.shagaev.node.TreeNode;

public class Tree<T> {
    private TreeNode<T> root;
    private int size;

    public Tree() {
        root = null;
        size = 0;
    }

    public boolean searchTreeNode(T data) {
        TreeNode<T> current = root;

        while (current != null && !data.equals(current.getData())) {
            if (current.isDataGreaterThan(data)) {
                if (current.getLeftChild() != null) {
                    current = current.getLeftChild();
                } else {
                    return false;
                }
            } else {
                if (current.getRightChild() != null) {
                    current = current.getRightChild();
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    public void insertionInTree(T data) {
        TreeNode<T> current = root;

        while (current != null) {
            if (current.isDataGreaterThan(data)) {
                if (current.getLeftChild() != null) {
                    current = current.getLeftChild();
                } else {
                    current.setLeftChild(new TreeNode<>(data));
                    return;
                }
            } else {
                if (current.getRightChild() != null) {
                    current = current.getRightChild();
                } else {
                    current.setRightChild(new TreeNode<>(data));
                    return;
                }
            }
        }

        root = new TreeNode<>(data);
        size++;
    }

    public TreeNode<T> deleteNode(TreeNode<T> root, T data) {
        if (root == null) {
            return null;
        }

        if (root.isDataGreaterThan(data)) {
            root.setLeftChild(deleteNode(root.getLeftChild(), data));
        } else if (root.isDataLessThan(data)) {
            root.setRightChild(deleteNode(root.getRightChild(), data));
        } else {
            if (root.getLeftChild() == null) {
                return root.getRightChild();
            } else if (root.getRightChild() == null) {
                return root.getLeftChild();
            }

            TreeNode<T> minRightNode = findMin(root.getRightChild());
            root.setData(minRightNode.getData());
            root.setRightChild(deleteNode(root.getRightChild(), minRightNode.getData()));
        }

        size--;
        return root;
    }

    private TreeNode<T> findMin(TreeNode<T> node) {
        while (node.getLeftChild() != null) {
            node = node.getLeftChild();
        }
        return node;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringHelper(root, sb);
        return sb.toString().trim();
    }

    private void toStringHelper(TreeNode<T> node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        sb.append(node.getData()).append(" ");
        toStringHelper(node.getLeftChild(), sb);
        toStringHelper(node.getRightChild(), sb);
    }
}