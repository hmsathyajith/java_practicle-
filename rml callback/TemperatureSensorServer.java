import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;

import javax.sql.rowset.serial.SerialStruct;

import javafx.scene.CacheHint;

public class TemperatureSensorServer extends UnicastRemoteObject implements TemperatureSensor, Runnable {

    private volatile double temp;
    private ArrayList<TemperatureListener> list = new ArrayList<TemperatureListener>();

    public TemperatureSensorServer() throws java.rmi.RemoteException{
        temp = 98.0;
    }

    public double getTemperature() throws java.rmi.RemoteException{
        return temp;
    }
        

    public void addTemperatureListener(TemperatureListener listener) throws java.rmi.RemoteException{
        System.out.println("adding listener - " + listener);
        list.add(listener);
    }

    public void removeTemperatureListener(TemperatureListener listener) throws java.rmi.RemoteException{
        System.out.println("removing listener - "+ listener);
                list.remove(listener);
    }

    public void run(){
        Random r = new Random();

        for(;;){
            try{
                int duration = r.nextInt()%10000 +200;
                if(duration < 0){
                    duration = duration * -1;
                    Thread.sleep(duration);
                }
            }
                catch(InterruptedException ie){

                }

                int num = r.nextInt();
                if(num<0){
                    temp += 0.5;
                }else{
                    temp -= 0.5;
                }

                notifyListeners(temp);
            }       
        }

    public void notifyListeners(double aInTemp){
       for(TemperatureListener iListener : list){
           try{
               iListener.temperatureChanged(aInTemp);
           }catch(RemoteException ie){
               list.remove(iListener);
           }
       }
    }

    public static void main(String[] args) {

        System.setProperty("java.security.policy", "file:allowall.policy");
  
 
         System.out.println("Loading temperature service");
 
         try {
             TemperatureSensorServer sensor = new TemperatureSensorServer();
             String registry = "localhost";
 
             String registration = "rmi://" + registry + "/TemperatureSensor";
 
             Naming.rebind(registration, sensor);
 
             Thread thread = new Thread(sensor);
             thread.start();
         } catch (RemoteException re) {
             System.err.println("Remote Error - " + re);
         } catch (Exception e) {
             System.err.println("Error - " + e);
         }
 
     }



}

