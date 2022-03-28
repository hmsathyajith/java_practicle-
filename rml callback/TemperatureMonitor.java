import java.net.*;
import java.rmi.*;
import java.rmi.server.*;



public class TemperatureMonitor extends UnicastRemoteObject implements TemperatureListener {

    private int count = 0;

    public TemperatureMonitor() throws RemoteException{

    }

    public static void main(String[] args) {

        System.setProperty("java.security.policy", "file:allowall.policy");

        try{
            String registration = "//localhost/TemperatureSensor";

            Remote remoteService = Naming.lookup(registration);
            TemperatureSensor sensor = (TemperatureSensor)remoteService;

            double reading = sensor.getTemperature();
            System.out.println("Original temp: "+ reading);

            TemperatureMonitor monitor = new TemperatureMonitor();
            sensor.addTemperatureListener(monitor);
          
            monitor.run();

        }catch(MalformedURLException mue){
        }catch(RemoteException re){
        }catch(NotBoundException nbe){
        }


        
    }

    public void temperatureChanged(double temperature) throws java.rmi.RemoteException{
        System.out.println("Temerature change event " + temperature);
        count = 0;
    }

    public void run(){
        for(;;){
            count ++;
            System.out.println("\r" + count);

            try{
                Thread.sleep(1000);
            }catch(InterruptedException ie){

            }
        }
    }
    
}
