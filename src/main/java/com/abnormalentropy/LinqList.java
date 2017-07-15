package com.abnormalentropy;

import com.abnormalentropy.exceptions.EmptyListException;
import com.abnormalentropy.exceptions.NoSingleElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.*;

/**
 * Created by rohan on 2017/05/14.
 */
public class LinqList<T> extends ArrayList<T>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(LinqList.class);

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

    List<T> toList()
    {
        return new ArrayList<T>(this);
    }

    <K> HashMap<K, T> toHashMap(Function<T, K> function)
    {
        HashMap<K, T> hashMap = new HashMap<K, T>();

        this.forEach(x -> hashMap.put(function.apply(x), x));

        return hashMap;
    }

    LinqList<T> union(List<T> list)
    {
        LinqList<T> union = new LinqList<T>(this);

        for (T x : list)
            if (!union.contains(x))
                union.add(x);

        return union;
    }

    LinqList<T> union(List<T> list, Comparator<T> comparator)
    {
        LinqList<T> union = new LinqList<T>(this);

        for (T x : list)
            if (!union.contains(x, comparator))
                union.add(x);

        return union;
    }

    <U, R> LinqList<R> zip(List<U> list, BiFunction<T, U, R> function)
    {
        LinqList<R> linqList = new LinqList<R>();

        int end = Math.min(this.size(), list.size());
        for (int k = 0; k < end; k++)
            linqList.add(function.apply(this.get(k), list.get(k)));

        return linqList;
    }

    LinqList<T> take(int size)
    {
        return new LinqList<T>(this.subList(0, size));
    }

    LinqList<T> skip(int from)
    {
        return new LinqList<T>(this.subList(from, this.size()));
    }

    T single() throws NoSingleElementException
    {
        if (this.size() > 1)
            throw new NoSingleElementException();

        return this.get(0);
    }

    T single(Function<T, Boolean> function) throws NoSingleElementException
    {
        return this.where(function).single();
    }

    T singleOrDefault()
    {
        return (this.size() > 1) ? null : this.get(0);
    }

    T singleOrDefault(Function<T, Boolean> function)
    {
        return this.where(function).singleOrDefault();
    }

    LinqList<T> concat(LinqList<T> list)
    {
        LinqList<T> returnList = new LinqList<T>(list);
        list.forEach(x -> returnList.add(x));

        return returnList;
    }

    int count(Function<T, Boolean> function)
    {
        return this.where(function).size();
    }

    LinqList<T> distinct()
    {
        LinqList<T> ret = new LinqList<T>();
        this.forEach(x -> {
            if (!ret.contains(x))
                ret.add(x);
        });

        return ret;
    }

    LinqList<T> distinct(Comparator<T> comparator)
    {
        LinqList<T> ret = new LinqList<T>();
        this.forEach(x -> {
            if (!ret.contains(x, comparator))
                ret.add(x);
        });

        return ret;
    }
}
