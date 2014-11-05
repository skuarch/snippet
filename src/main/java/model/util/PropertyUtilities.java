package model.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Utilities for Properties file.
 *
 * @author skuarch
 */
public class PropertyUtilities {

    private String fileProperties = null;

    //==========================================================================
    public PropertyUtilities(String fileProperties) {
        this.fileProperties = fileProperties;
    } // end PropertyUtilities      

    //==========================================================================
    /**
     * return a Properties.
     *
     * @return Properties or null if the file doesn't exists.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Properties getProperties() throws FileNotFoundException, IOException {

        if (fileProperties == null || fileProperties.length() < 1) {
            throw new NullPointerException("fileProperties is null");
        }

        FileInputStream fis = null;
        Properties properties = null;

        try {

            fis = new FileInputStream(fileProperties);
            properties = new Properties();
            properties.load(fis);

        } catch (FileNotFoundException fnfe) {
            throw fnfe;
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            closeInputStream(fis);
            fis = null;
        }

        return properties;

    } // end getProperties

    //==========================================================================
    /**
     * return String with the value of the key.
     *
     * @param key String
     * @return String with the value of the key
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String getStringProperty(String key) throws FileNotFoundException, IOException {

        if (fileProperties == null || fileProperties.length() < 1) {
            throw new NullPointerException("fileProperties is null or empty");
        }

        if (key == null || key.length() < 1) {
            throw new NullPointerException("key is null or empty");
        }

        Properties p = getProperties();
        return p.getProperty(key);

    } // end getStringPropertie

    //==========================================================================
    /**
     * write properties on file. this action will erase the contents of the file
     * and will put the new content
     *
     * @param properties Properties to save
     * @throws IOException
     */
    public void storeProperties(Properties properties) throws IOException {

        if (properties == null || properties.size() < 1) {
            throw new NullPointerException("properties is null or empty");
        }

        FileOutputStream fos = null;

        try {

            fos = new FileOutputStream(fileProperties);
            properties.store(fos, null);

        } catch (IOException ioe) {
            throw ioe;
        } finally {
            closeOutputStream(fos);
        }

    } // end storeProperties

    //==========================================================================
    /**
     * append a property into the file.
     */
    public void putProperty(Object key, Object value) throws IOException {

        Properties properties = null;

        properties = getProperties();
        properties.put(key, value);

        storeProperties(properties);

    } // end putPropertie

    //==========================================================================
    /**
     * return int with the value of the key.
     *
     * @param key String
     * @return int with the value of the key
     * @throws FileNotFoundException
     * @throws IOException
     */
    public int getIntProperty(String key) throws FileNotFoundException, IOException {

        if (fileProperties == null || fileProperties.length() < 1) {
            throw new NullPointerException("fileProperties is null or empty");
        }

        if (key == null || key.length() < 1) {
            throw new NullPointerException("key is null or empty");
        }

        int value;

        value = Integer.parseInt(getStringProperty(key));

        return value;

    } // end getIntPropertie
    
    //==========================================================================
    /**
     * return a short value from properties file.
     * @param key String 
     * @return short
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public short getShortProperty(String key) throws FileNotFoundException, IOException {

        if (fileProperties == null || fileProperties.length() < 1) {
            throw new NullPointerException("fileProperties is null or empty");
        }

        if (key == null || key.length() < 1) {
            throw new NullPointerException("key is null or empty");
        }

        short value;

        value = Short.parseShort(getStringProperty(key));        

        return value;

    } // end getIntPropertie

    //==========================================================================
    /**
     * close the inputStream.
     *
     * @param inputStream
     */
    private static void closeInputStream(InputStream inputStream) throws IOException {

        try {

            if (inputStream != null) {
                inputStream.close();
            }

        } catch (IOException e) {
            throw e;
        }

    } // end closeInputStream

    //==========================================================================
    /**
     * close the outputStream.
     *
     * @param inputStream
     */
    private static void closeOutputStream(OutputStream outputStream) throws IOException {

        try {

            if (outputStream != null) {
                outputStream.close();
            }

        } catch (IOException e) {
            throw e;
        }

    } // end closeInputStream
    
} // end class
