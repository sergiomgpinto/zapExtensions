package org.zaproxy.addon.attackprevention.database;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

public class Credentials {

    private final String name;
    private String hashedPasswd;
    private byte[] salt;
    private static final String CREDENTIALS_SEPARATOR = ":credentialseperator:";

    /**
     * Used when data is loaded from database.
     */
    private Credentials(String name, String passwd, byte[] salt) {
        this.name = name;
        this.hashedPasswd = passwd;
        this.salt = salt;
    }

    /**
     * Main constructor. Note: this is not to be used directly.
     * If you want a Credentials object, get one through the
     * history interface.
     */
    public static Credentials createCredentials(String name, String passwd) {
        Credentials creds = new Credentials(name, passwd, null);
        byte[] hashedPasswd = creds.generateHashedPwd(passwd);
        if (hashedPasswd == null)
            return null;

        creds.hashedPasswd = new String(hashedPasswd);

        return creds;
    }

    /**
     * Hash the given string with a newly generated salt.
     */
    private byte[] generateHashedPwd(String pwd) {

        SecureRandom random = new SecureRandom();
        salt = new byte[32];
        random.nextBytes(salt);

        return hashPwd(salt, pwd);
    }

    /**
     * Hash a string with the given salt.
     */
    private byte[] hashPwd(byte[] salt, String pwd) {
        byte[] localHashedPasswd = null;
        try {
            KeySpec spec = new PBEKeySpec(pwd.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            localHashedPasswd = factory.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return localHashedPasswd;
    }

    /**
     * Used for checking if a record already exists because a newly created
     * instance will have a different salt and thus passwd.
     */
    public boolean matches(String name, String pwd) {
        byte[] hashedPassword = hashPwd(salt, pwd);
        if (hashedPassword == null)
            return false;

        return this.name.equals(name) && this.hashedPasswd.equals(new String(hashedPassword));
    }

    /**
     * Used for indexing in map.
     */
    @Override
    public int hashCode(){
        return Objects.hash(name, hashedPasswd, Arrays.hashCode(salt));
    }

    /**
     * Used for indexing in map, keep in mind that this only works with the exact same object.
     */
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Credentials))
            return false;

        Credentials creds = (Credentials) o;
        return name.equals(creds.name) && hashedPasswd.equals(creds.hashedPasswd)
                && new String(salt).equals(new String(creds.salt));
    }

    /**
     * Serialisation to string. Used for storage.
     */
    @Override
    public String toString() {
        return this.name + CREDENTIALS_SEPARATOR + this.hashedPasswd + CREDENTIALS_SEPARATOR
                + Base64.getEncoder().encodeToString(this.salt);
    }

    /**
     * Deserialisation from string. Used for storage.
     */
    public static Credentials fromString(String s) {

        if (s == null) {
            return null;
        }

        String[] split = s.split(CREDENTIALS_SEPARATOR);
        return new Credentials(split[0], split[1], Base64.getDecoder().decode(split[2]));
    }
}
