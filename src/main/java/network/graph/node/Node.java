package network.graph.node;

public abstract class Node {

    public int id;
    public String name;

    public Node(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Node && ((Node) o).id == this.id;
    }
}
