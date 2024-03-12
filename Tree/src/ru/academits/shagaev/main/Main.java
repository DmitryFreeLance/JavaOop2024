package ru.academits.shagaev.main;

import ru.academits.shagaev.tree.Tree;

public class Main {
    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>();
        tree.insertionInTree(3);
        tree.insertionInTree(2);
        tree.insertionInTree(5);

        System.out.println(tree);
    }
}
