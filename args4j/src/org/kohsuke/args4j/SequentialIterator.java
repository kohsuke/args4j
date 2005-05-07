package org.kohsuke.args4j;

import java.util.Iterator;

/**
 * {@link Iterator} that combines two other {@link Iterator}s.
 * 
 * @author
 *      Kohsuke Kawaguchi (kk@kohsuke.org)
 */
final class SequentialIterator implements Iterator {
    private Iterator lhs,rhs;
    
    SequentialIterator( Iterator lhs, Iterator rhs ) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public boolean hasNext() {
        if(lhs!=null) {
            boolean r = lhs.hasNext();
            if(r)   return true;
            lhs=null;
        }
        return rhs.hasNext();
    }

    public Object next() {
        if(lhs!=null)   return lhs.next();
        else            return rhs.next();
    }
}
