package graph.node;

import java.util.Objects;

public class ServerNode extends Node {
    public String name;
    public String host;

    public ServerNode(int id, String name, String host, int port, int totalRequests) {
        super(id);
        this.name = name;
        this.host = host;
        this.port = port;
        this.totalRequests = totalRequests;
    }

    public int port;
    public int totalRequests = 0;

    public ServerNode(int id) {
        super(id);
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ServerNode && ((ServerNode) o).name.equals(this.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

