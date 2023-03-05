package com.keep.sso.ticket.utils;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public final class EncodingUtils {

    /**
     * JSON web key parameter that identifies the key..
     */
    public static final String JSON_WEB_KEY = "k";

    private static final Logger LOGGER = LoggerFactory.getLogger(EncodingUtils.class);

    private EncodingUtils() {
    }


    public static String encodeBase64(final byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }


    public static byte[] decodeBase64(final String data) {
        return Base64.getDecoder().decode(data);
    }


    public static byte[] decodeBase64(final byte[] data) {
        return Base64.getDecoder().decode(data);
    }


    public static byte[] encodeBase64ToByteArray(final byte[] data) {
        return Base64.getEncoder().encode(data);
    }



    public static String urlEncode(final String value) {
        return urlEncode(value, StandardCharsets.UTF_8.name());
    }


    public static String urlEncode(final String value, final String encoding) {
        try {
            return URLEncoder.encode(value, encoding);
        } catch (final UnsupportedEncodingException e) {
            throw Throwables.propagate(e);
        }
    }


    public static String urlDecode(final String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.name());
        } catch (final UnsupportedEncodingException e) {
            throw Throwables.propagate(e);
        }
    }

//    public static byte[] verifyJwsSignature(final Key signingKey, final byte[] value) {
//        try {
//            final String asString = new String(value, StandardCharsets.UTF_8);
//            final JsonWebSignature jws = new JsonWebSignature();
//            jws.setCompactSerialization(asString);
//            jws.setKey(signingKey);
//
//            final boolean verified = jws.verifySignature();
//            if (verified) {
//                final String payload = jws.getPayload();
//                LOGGER.trace("Successfully decoded value. Result in Base64-encoding is [{}]", payload);
//                return EncodingUtils.decodeBase64(payload);
//            }
//            return null;
//        } catch (final Exception e) {
//            throw Throwables.propagate(e);
//        }
//    }

    public static void main(String[] args) {
        System.out.printf(encodeBase64(("12345" + "2wsxedc" + "-1.0").getBytes()));
    }

}
