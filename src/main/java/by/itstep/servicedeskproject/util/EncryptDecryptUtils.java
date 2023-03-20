package by.itstep.servicedeskproject.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class EncryptDecryptUtils {
    public static final String SECRET_KEY_1 = "ssdkF$HUy2A#D%kd";
    public static final String SECRET_KEY_2 = "weJiSEvR5yAC5ftB";
    private static IvParameterSpec ivParameterSpec;
    private static SecretKeySpec secretKeySpec;
    private static Cipher cipher;

    static {
        try {
            ivParameterSpec = new IvParameterSpec(SECRET_KEY_1.getBytes("UTF-8"));
            secretKeySpec = new SecretKeySpec(SECRET_KEY_2.getBytes("UTF-8"), "AES");
            // стандарт шифрования AES
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public static String encrypt(String toBeEncrypt) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(toBeEncrypt.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String decrypt(String encrypted) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args){

        String encrPass = EncryptDecryptUtils.encrypt("789");
        System.out.println(encrPass);
        System.out.println(EncryptDecryptUtils.decrypt("rq40DAMvUF4nyP1xxKEKwA=="));
    }
}