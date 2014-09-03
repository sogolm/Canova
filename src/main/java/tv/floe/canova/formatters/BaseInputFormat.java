package tv.floe.canova.formatters;


import java.io.*;

/**
 * Created by mjk on 8/28/14.
 */

public abstract class BaseInputFormat<T> implements IInputFormat<T>, Serializable {
    protected static final String COMMA = new String(",");


    /**
     *
     * @param is
     * @return
     * @throws IOException
     */
    @Override
    public abstract T read(InputStream is, String delim) throws IOException;

    /**
     *
     * @param is
     * @return
     * @throws IOException
     */
    @Override
    public abstract T read(String is, String delim) throws IOException;

    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public abstract T read(File file, String delim) throws IOException;

    /**
     *
     * @param ins
     * @return
     */
    protected String[] stringSplitComma(String ins) {
        return stringSplit(ins, new String(","));
    }

    /**
     *
     * @param ins
     * @param delim
     * @return
     */
    protected String[] stringSplit(String ins, String delim) {
        String[] tokens = ins.split(delim);
        return tokens;
    }

    /**
     * Read/deserialize and object from a String object
     * @param is
     * @param delim
     * @return
     * @throws IOException
     */
    protected T readObj(String is, String delim) throws IOException {
        byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(is);

        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        T ret = read(stream, delim);
        stream.close();
        return ret;
    }

    /**
     * Read/deserialize and object from an input stream
     * @param is
     * @param delim
     * @return
     * @throws IOException
     */
    public T readObj(InputStream is, String delim) throws IOException {
        ObjectInputStream objin = new ObjectInputStream(is);
        T cv = null;
        try {
            cv = (T) objin.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cv;
    }

    /**
     * Read/deserialize and object from a File object
     * @param file
     * @param delim
     * @return
     * @throws IOException
     */
    public T readObj(File file, String delim) throws IOException {
        FileInputStream fs = new FileInputStream(file);
        T cv = read(fs, delim);
        fs.close();
        return cv;
    }

    public static final String DELIM_NEWLINE  = new String("\n");
    public static final String DELIM_COLON  = new String(":");
    public static final String DELIM_BAR  = new String("|");
    public static final String SPACE  = new String(" ");
    public static final String DELIM_COMMA  = new String(",");

    /**
     * Read inputstream until delim is encountered; single byte character for now.
     * @param delim -- a single byte character that demarcates the stream
     * @param is
     * @return
     * @throws IOException
     */
    protected static String readLine(String delim, InputStream is) throws IOException {
        String ret = null;
        int c;
        while ( (c = is.read()) != -1 ) {
            if (ret == null) {
                ret = new String();
            }
            char ch = (char) c;
            if (ch == (delim.charAt(0))) {
                break;
            }
            ret += new String(String.valueOf(ch));
        }
        return ret;
    }

}
