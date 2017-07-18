package com.abnormalentropy;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by rohan on 2017/05/14.
 */
public class LinqListTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(LinqListTest.class);

    @Test
    public void constructionTest()
    {
        LOGGER.info("Running construction test");
        LinqList<Integer> linqList = new LinqList<Integer>();

        Assert.assertEquals(0, linqList.size());

        linqList.add(2);
        linqList.add(3);
        linqList.add(4);
        Assert.assertEquals(3, linqList.size());

        linqList.remove(0);
        Assert.assertEquals(2, linqList.size());
    }

    @Test
    public void listConstructionTest()
    {
        LOGGER.info("Running list construction test");
        ArrayList<Integer> arrayList = new ArrayList<Integer>();

        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(2);

        LinqList<Integer> linqList = new LinqList<Integer>();
        linqList.addAll(arrayList);

        Assert.assertEquals(arrayList.size(), linqList.size());
    }

    @Test
    public void whereGreaterThanTest()
    {
        LOGGER.info("Running where lambda (greater than) test");
        LinqList<Integer> linqList = new LinqList<Integer>();
        linqList.add(0);
        linqList.add(1);
        linqList.add(2);
        linqList.add(3);

        LinqList<Integer> newList = linqList.where(x -> x >= 2);

        LOGGER.debug(newList.toString());

        Assert.assertEquals(2, newList.size());
        Assert.assertEquals(2, newList.get(0).intValue());
        Assert.assertEquals(3, newList.get(1).intValue());
    }

    @Test
    public void whereLessThanTest()
    {
        LOGGER.info("Running where lambda (less than) test");
        LinqList<Integer> linqList = new LinqList<Integer>();
        linqList.add(0);
        linqList.add(1);
        linqList.add(2);
        linqList.add(3);

        LinqList<Integer> newList = linqList.where(x -> x < 2);

        LOGGER.debug(newList.toString());

        Assert.assertEquals(2, newList.size());
        Assert.assertEquals(0, newList.get(0).intValue());
        Assert.assertEquals(1, newList.get(1).intValue());
    }

    @Test
    public void whereBoundedTest()
    {
        LOGGER.info("Running where lambda (bounded) test");
        LinqList<Integer> linqList = new LinqList<Integer>();
        linqList.add(0);
        linqList.add(1);
        linqList.add(2);
        linqList.add(3);

        LinqList<Integer> newList = linqList.where(x -> x >= 1 && x <= 2);

        LOGGER.debug(newList.toString());

        Assert.assertEquals(2, newList.size());
        Assert.assertEquals(1, newList.get(0).intValue());
        Assert.assertEquals(2, newList.get(1).intValue());
    }

    @Test
    public void anyExistsTest()
    {
        LOGGER.info("Running any exists test");
        LinqList<Integer> linqList = new LinqList<Integer>();
        linqList.add(0);
        linqList.add(1);
        linqList.add(2);
        linqList.add(3);

        Assert.assertEquals(true, linqList.any(x -> x == 2));
        Assert.assertEquals(false, linqList.any(x -> x == 5));
        Assert.assertEquals(true, linqList.any(x -> x != 5));
    }

    @Test
    public void allTest()
    {
        LOGGER.info("Running 'all' method test");
        LinqList<Integer> linqList = new LinqList<Integer>();
        linqList.add(0);
        linqList.add(1);
        linqList.add(2);
        linqList.add(3);

        Assert.assertEquals(true, linqList.all(x -> x > -1));
        Assert.assertEquals(false, linqList.all(x -> x < 2));
    }

    @Test
    public void comparatorContainsTest()
    {
        LOGGER.info("Running custom comparator contains test");
        LinqList<Integer> linqList = new LinqList<Integer>();
        linqList.add(0);
        linqList.add(1);
        linqList.add(2);
        linqList.add(3);

        Comparator<Integer> comparator = Comparator.comparing(Object::toString);

        Assert.assertEquals(true, linqList.contains(2, comparator));
        Assert.assertEquals(false, linqList.contains(10, comparator));
    }

    @Test
    public void unionTest()
    {
        LOGGER.info("Running union test");
        LinqList<Integer> linqList = new LinqList<Integer>();
        linqList.add(0);
        linqList.add(1);
        linqList.add(2);
        linqList.add(3);

        LinqList<Integer> linqList2 = new LinqList<Integer>();
        linqList2.add(0);
        linqList2.add(1);
        linqList2.add(2);
        linqList2.add(3);

        LinqList<Integer> union = linqList.union(linqList2);
        LOGGER.debug(union.toString());

        Assert.assertEquals(linqList.size(), union.size());

        linqList2.add(5);
        union = linqList.union(linqList2);

        Assert.assertEquals(5, union.size());
        LOGGER.debug(union.toString());
    }

    @Test
    public void concatTest()
    {
        LOGGER.info("Running concat test");
        LinqList<Integer> linqList = new LinqList<Integer>();
        linqList.add(0);
        linqList.add(1);
        linqList.add(2);
        linqList.add(3);

        LinqList<Integer> linqList2 = new LinqList<Integer>();
        linqList2.add(0);
        linqList2.add(1);
        linqList2.add(2);
        linqList2.add(3);

        LinqList<Integer> concat = linqList.concat(linqList2);

        Assert.assertEquals(linqList.size() + linqList2.size(), concat.size());

        LOGGER.debug(linqList.toString());
        LOGGER.debug(linqList2.toString());
        LOGGER.debug(concat.toString());

    }

    @Test
    public void countTest()
    {
        LOGGER.info("Running count test");
        LinqList<Integer> linqList = new LinqList<Integer>();
        linqList.add(0);
        linqList.add(1);
        linqList.add(2);
        linqList.add(3);

        Assert.assertEquals(2, linqList.count(x -> x >= 2));
    }

    @Test
    public void distinctTest()
    {
        LOGGER.info("Running distinct test");
        LinqList<Integer> linqList = new LinqList<Integer>();
        linqList.add(0);
        linqList.add(1);
        linqList.add(2);
        linqList.add(2);

        linqList = linqList.distinct();

        Assert.assertEquals(3, linqList.size());

        LOGGER.debug(linqList.toString());
    }

    @Test
    public void doubleSumTest()
    {
        LOGGER.info("Running double sum test");
        LinqList<Integer> linqList = new LinqList<Integer>();
        linqList.add(0);
        linqList.add(1);
        linqList.add(2);
        linqList.add(3);

        double sum = linqList.sum(x -> (double) x);

        Assert.assertEquals(6.0, sum, 0.0);

        LOGGER.debug(String.valueOf(sum));
    }

    @Test
    public void doubleAverageTest()
    {
        LOGGER.info("Running double average test");
        LinqList<Integer> linqList = new LinqList<Integer>();
        linqList.add(1);
        linqList.add(2);
        linqList.add(3);

        double average = linqList.average(x -> (double) x);

        Assert.assertEquals(2.0, average, 0.0);

        LOGGER.debug(String.valueOf(average));
    }
}
