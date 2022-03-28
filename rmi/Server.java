import java.rmi.*;

public class Server {
    public static void main(String[] args) throws Exception {
        AddClass obj = new AddClass();
        Naming.rebind("ADD", obj);
        System.out.println("Server Started");
    }
}
