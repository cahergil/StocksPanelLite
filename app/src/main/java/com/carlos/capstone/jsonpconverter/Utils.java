package com.carlos.capstone.jsonpconverter;

/**
 * Created by Carlos on 22/12/2015.
 */
import java.io.Closeable;
import java.io.IOException;

/**
 * Created by jamesanto on 12/23/15.
 */
public final class Utils {
    static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException ignored) {
        }
    }
}