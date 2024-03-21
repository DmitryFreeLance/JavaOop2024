package ru.academits.shagaev.main;

import ru.academits.shagaev.tree.Tree;

public class Main {
    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>();
        tree.insertionInTree(3);
        tree.insertionInTree(10);
        tree.insertionInTree(1);
        tree.insertionInTree(6);
        tree.insertionInTree(4);
        tree.insertionInTree(7);
        tree.insertionInTree(14);
        tree.insertionInTree(13);

        tree.deleteNode(tree.getRoot(), 4);

        System.out.println("Размер дерева = " + tree.getSize());
        System.out.println("Поиск числа 4 в дереве: " + tree.searchTreeNode(4));

        tree.depthFirstTraversalRecursion(tree.getRoot());
        tree.depthFirstTraversal();
        tree.breadthFirstTraversal();
    }
}