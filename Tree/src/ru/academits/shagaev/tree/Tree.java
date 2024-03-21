package ru.academits.shagaev.tree;

import ru.academits.shagaev.node.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

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
                    size++;
                    return;
                }
            } else {
                if (current.getRightChild() != null) {
                    current = current.getRightChild();
                } else {
                    current.setRightChild(new TreeNode<>(data));
                    size++;
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

    public int getSize() {
        return size;
    }

    public void breadthFirstTraversal() {
        if (root == null) {
            return;
        }

        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<T> currentElement = queue.poll();
            System.out.println(currentElement.getData());

            if (currentElement.getLeftChild() != null) {
                queue.add(currentElement.getLeftChild());
            }

            if (currentElement.getRightChild() != null) {
                queue.add(currentElement.getRightChild());
            }
        }
    }

    public void depthFirstTraversalRecursion(TreeNode<T> node) {
        if (node == null) {
            return;
        }

        visit(node);

        List<TreeNode<T>> children = node.getChildren();
        if (children != null) {
            for (TreeNode<T> child : children) {
                depthFirstTraversalRecursion(child);
            }
        }
    }

    private void visit(TreeNode<T> node) {
        System.out.println(node.getData() + " ");
    }

    public void depthFirstTraversal() {
        if (root == null) {
            return;
        }

        Stack<TreeNode<T>> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode<T> currentElement = stack.pop();
            System.out.println(currentElement.getData());

            if (currentElement.getRightChild() != null) {
                stack.add(currentElement.getRightChild());
            }

            if (currentElement.getLeftChild() != null) {
                stack.add(currentElement.getLeftChild());
            }
        }
    }

    public TreeNode<T> getRoot() {
        return root;
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