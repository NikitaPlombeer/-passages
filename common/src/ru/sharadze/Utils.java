package ru.sharadze;

import java.io.*;

public class Utils {
    public static int[] serialize(Object o) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(o);
            out.flush();
            byte[] bytes = bos.toByteArray();

            int[] arr = new int[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                arr[i] = bytes[i];
            }
            return arr;
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }

    public static <T> T deserialize(int[] data, Class<T> clz) throws IOException, ClassNotFoundException {
        byte[] array = new byte[data.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) data[i];
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(array);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            return clz.cast(o);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
