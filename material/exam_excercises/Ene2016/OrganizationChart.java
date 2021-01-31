package material.exam_excercises.Ene2016;

import material.tree.narytree.LinkedTree;
import material.tree.narytree.NAryTree;

public class OrganizationChart {
    private NAryTree<Employee> tree;

    public OrganizationChart() {
        this.tree = new LinkedTree<>();
    }

    public OrganizationChart(NAryTree<Employee> tree) {
        this.tree = tree;
    }

    public NAryTree<Employee> getTree() {
        return tree;
    }

    public void setTree(NAryTree<Employee> tree) {
        this.tree = tree;
    }
}
