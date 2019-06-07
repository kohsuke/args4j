package org.kohsuke.args4j.apt;

/**
 * @author Kohsuke Kawaguchi
 */
public class IsolatingClassLoader extends ClassLoader {
    public IsolatingClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        throw new ClassNotFoundException(name);
    }

    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if(!name.startsWith("org.kohsuke"))
            return super.loadClass(name,resolve);
        throw new ClassNotFoundException(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        throw new ClassNotFoundException(name);
    }
}
