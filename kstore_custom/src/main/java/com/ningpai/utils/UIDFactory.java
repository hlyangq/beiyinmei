package com.ningpai.utils;

import com.ningpai.util.MyLogger;

import java.net.InetAddress;
import java.security.MessageDigest;
import java.util.Random;

/**
 * <p>
 * </p>
 *
 * @version 1.0
 */
public abstract class UIDFactory {
    public static final MyLogger LOGGER = new MyLogger(UIDFactory.class);
    /**
     * Global Unified Identifier
     */
    public static final String UID_GUID = "GUID";

    /**
     * United Unified Identifier
     */
    public static final String UID_UUID = "UUID";

    /**
     * Current Epoch millis SEED
     */
    protected static final long EPOCH = System.currentTimeMillis();

    /**
     * JVM Hashcode
     */
    protected static final long JVMHASH = Integer.MIN_VALUE;

    /**
     * Epoch has millisecond
     */
    protected static final long MACHINEID = getMachineID();

    /**
     * Random by seed
     */
    protected static final Random M_RANDOM = new Random(EPOCH);

    /**
     * MD5 Instance
     */
    private static MessageDigest md5;

    /* Initialize MD5 factory */
    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (java.security.NoSuchAlgorithmException ex) {
            LOGGER.info(ex);
        }
    }

    /**
     * MD5 flag
     */
    private boolean isMd5 = false;

    /**
     * Get Default UIDFactory.
     *
     * @return UIDFactory UID manager object
     */
    public static UIDFactory getDefault() {
        return UUID.getInstance();
    }

    /**
     * Get Specified UIDFactory.
     *
     * @param uidfactory Description of the Parameter
     * @return UIDFactory
     * @throws Exception Description of the Exception
     */
    public static UIDFactory getInstance(String uidfactory) throws Exception {
        if (uidfactory.equalsIgnoreCase(UID_UUID)) {
            return UUID.getInstance();
        }

        throw new Exception(uidfactory + " Not Found!");
    }

    /**
     * Get next UID.
     *
     * @return String Storagable UID
     */
    public abstract String getNextUID();

    /**
     * Get current UID.
     *
     * @return String Storagable UID
     */
    public abstract String getUID();

    /**
     * Is MD5 switch ON.
     *
     * @return ON is true.
     */
    public boolean isMD5() {
        return isMd5;
    }

    /**
     * Set MD5 output.
     *
     * @param flag MD5 switch
     */
    public void setMD5(boolean flag) {
        isMd5 = flag;
    }

    /**
     * Set current UID.
     *
     * @param uid Object uid
     * @throws Exception Description of the Exception
     */
    public abstract void setUID(String uid) throws Exception;

    /**
     * Return Printable ID String.
     *
     * @return String
     */
    public abstract String toPrintableString();

    /**
     * Convert bytes to MD5 bytes.
     *
     * @param bytes Description of the Parameter
     * @return
     */
    protected static byte[] toMD5(byte[] bytes) {
        return md5.digest(bytes);
    }

    /**
     * Gets the machineID attribute of the GUID class
     *
     * @return The machineID value
     */
    private static long getMachineID() {
        long i = 0;

        try {
            InetAddress inetaddress = InetAddress.getLocalHost();
            byte[] abyte0 = inetaddress.getAddress();

            i = toInt(abyte0);
        } catch (Exception ex) {
            LOGGER.info(ex);
        }

        return i;
    }

    /**
     * Convert bytes to int utils.
     *
     * @param abyte0 Object bytes array
     * @return Result int
     */
    private static int toInt(byte[] abyte0) {
        int i = ((abyte0[0] << 24) & 0xff000000) | ((abyte0[1] << 16) & 0xff0000) | ((abyte0[2] << 8) & 0xff00)
                | (abyte0[3] & 0xff);

        return i;
    }

}
