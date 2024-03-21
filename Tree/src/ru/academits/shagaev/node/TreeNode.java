package ru.academits.shagaev.node;

import java.util.List;

public class TreeNode<T> {
    private TreeNode<T> left;
    private TreeNode<T> right;
    private List<TreeNode<T>> children;
    private T data;

    public TreeNode(T data) {
        this.data = data;
        left = null;
        right = null;
        children = null;
    }

    public boolean isDataLessThan(T value) {
        if (data instanceof Comparable) {
            return ((Comparable<T>) data).compareTo(value) < 0;
        } else {
            throw new IllegalArgumentException("Тип T должен реализовывать Comparable<T>");
        }
    }

    public boolean isDataGreaterThan(T value) {
        if (data instanceof Comparable) {
            return ((Comparable<T>) data).compareTo(value) > 0;
        } else {
            throw new IllegalArgumentException("Тип T должен реализовывать Comparable<T>");
        }
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public TreeNode<T> getLeftChild() {
        return left;
    }

    public TreeNode<T> getRightChild() {
        return right;
    }

    public void setLeftChild(TreeNode<T> leftChild) {
        left = leftChild;
    }

    public void setRightChild(TreeNode<T> rightChild) {
        right = rightChild;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }
}