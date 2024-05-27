package graph.node;

public abstract class Node {

    public int id;

    public Node(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Node && ((Node) o).id == this.id;
    }
}
