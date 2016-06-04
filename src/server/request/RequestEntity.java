/**
 * BUSH1D0 ~ Jamie Purchase ~ 30/04/2016
 */
package server.request;

import java.util.HashMap;

/**
 *
 * @author Jamie
 */
public class RequestEntity
{
    private HashMap<String, Object> requestStore;
    
    public RequestEntity()
    {
        this.requestStore = new HashMap();
    }

    public Object storeGet(String key)
    {
        return this.requestStore.get(key);
    }
    
    public void storeSet(String key, Object value)
    {
        this.requestStore.put(key, value);
    }

}