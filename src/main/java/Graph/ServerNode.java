package Graph;

import java.util.Objects;

public class ServerNode {
    public String name;
    public String host;
    public int port;
    public int totalRequests = 0;

    public ServerNode(String host, int port, String name) {
        this.host = host;
        this.port = port;
        this.name = name;
        this.totalRequests = 10;
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

