package org.kohsuke.args4j.apt;

import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Used to isolate a child class loader from the parent classloader.
 *
 * @author Kohsuke Kawaguchi
 */
public class ReloadingClassLoader extends ClassLoader {
    protected ReloadingClassLoader(ClassLoader parent) {
        super(parent);
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String pathName = name.replace('.','/')+".class";

        InputStream is = getParent().getResourceAsStream(pathName);
        if (is==null)
            throw new ClassNotFoundException(name);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while((len=is.read(buf))>=0)
                baos.write(buf,0,len);

            buf = baos.toByteArray();
            return defineClass(name,buf,0,buf.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name,e);
        }
    }
}
