import java.rmi.server.*;

public class AddClass extends UnicastRemoteObject implements AddInterface {
    
    public AddClass() throws Exception{
        super();
    }
    
    public int add(int x, int y){
        return x+y;
    }
}
