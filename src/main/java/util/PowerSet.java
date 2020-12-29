package util;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class PowerSet<T> implements Iterator<List<T>> {
    boolean leftMode = true;
    List<T> memory = null;
    PowerSet<T> delegate;
    T pivot;

    public PowerSet(Iterator<T> storage) {
        if (storage.hasNext()) {
            this.pivot = storage.next();
            this.delegate = new PowerSet<>(storage);
        } else {
            this.delegate = null;
            this.pivot = null;
        }
    }

    public static void main(String[] args) {
        var input = List.of(5,5,3,2,1);
        var ps = new PowerSet<>(input.iterator());

        while (ps.hasNext()) {
            System.out.println(ps.next().stream().map(Object::toString).collect(Collectors.joining(",")));
        }
    }

    public boolean hasNext() {
        if (delegate == null) {
            boolean hasNext = leftMode;
            leftMode = false;
            return hasNext;
        }
        if (leftMode) {
            return delegate.hasNext();
        } else {
            return true;
        }
    }

    public List<T> next() {
        if (delegate == null) {
            return java.util.Collections.emptyList();
        }
        if (leftMode) {
            memory = delegate.next();
            leftMode = false;
            return memory;
        } else {
            leftMode = true;
            var list = new java.util.ArrayList<T>();
            list.add(pivot);
            list.addAll(memory);
            return list;
        }
    }

}