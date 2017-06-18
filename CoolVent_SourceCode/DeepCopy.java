
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;


/**
 * DeepCopy.java
 * Use to create deep copy of DataPools for different fan cases
 *
 * Created 2012
 * 
 * @author Alonso Dominguez
 * @version 8/16/2012
 */

public class DeepCopy {
    public static Object copy(Object orig) {
        Object obj = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(orig);
            oos.flush();
            oos.close();
            ObjectInputStream oins = new ObjectInputStream(
                new ByteArrayInputStream(baos.toByteArray()));
            obj = oins.readObject();
        } catch(IOException e) {
        } catch(ClassNotFoundException cnfe) {
        }
 
        return obj;
    }
}