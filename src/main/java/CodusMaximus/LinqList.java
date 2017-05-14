package CodusMaximus;

import CodusMaximus.exceptions.EmptyListException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.*;

/**
 * Created by rohan on 2017/05/14.
 */
public class LinqList<T> extends ArrayList<T>
{
    LinqList()
    {
        super();
    }

    LinqList(Collection<T> collection)
    {
        super(collection);
    }

    LinqList(int capacity)
    {
        super(capacity);
    }

    LinqList<T> where(Function<T, Boolean> function)
    {
         LinqList<T> linqList = new LinqList<T>();

         this.forEach(x -> {
            if (function.apply(x))
                linqList.add(x);
         });

         return linqList;
    }

    Boolean any(Function<T, Boolean> function)
    {
        for (T x : this)
            if (function.apply(x))
                return true;

        return false;
    }

    Boolean all(Function<T, Boolean> function)
    {
        for (T x : this)
            if (!function.apply(x))
                return false;

        return true;
    }

    Boolean contains(T elem, Comparator<T> comparator)
    {
        for (T x : this)
            if (comparator.compare(x, elem) == 0)
                return true;

        return false;
    }

    LinqList<T> intersect(LinqList<T> list)
    {
        LinqList<T> intersection = new LinqList<T>();

        LinqList<T> shortList = (list.size() < this.size()) ? list : this;
        LinqList<T> longList = (shortList == list) ? this : list;

        shortList.forEach(x -> {
            if (longList.contains(x))
                intersection.add(x);
        });

        return intersection;
    }

    LinqList<T> intersect(LinqList<T> list, Comparator<T> comparator)
    {
        LinqList<T> intersection = new LinqList<T>();

        LinqList<T> shortList = (list.size() < this.size()) ? list : this;
        LinqList<T> longList = (shortList == list) ? this : list;

        shortList.forEach(x -> {
            if (longList.contains(x, comparator))
                intersection.add(x);
        });

        return intersection;
    }

    T first() throws EmptyListException
    {
        if (this.isEmpty())
            throw new EmptyListException();

        return this.get(0);
    }

    T first(Function<T, Boolean> function) throws EmptyListException
    {
        return this.where(function).first();
    }

    T firstOrDefault()
    {
        return this.isEmpty() ? null : this.get(0);
    }

    T firstOrDefault(Function<T, Boolean> function)
    {
        return this.where(function).firstOrDefault();
    }

    T last() throws EmptyListException
    {
        if (this.isEmpty())
            throw new EmptyListException();

        return this.get(this.size()-1);
    }

    T last(Function<T, Boolean> function) throws EmptyListException
    {
        return this.where(function).last();
    }

    T lastOrDefault()
    {
        return this.isEmpty() ? null : this.get(this.size()-1);
    }

    T lastOrDefault(Function<T, Boolean> function)
    {
        return this.where(function).lastOrDefault();
    }
}
