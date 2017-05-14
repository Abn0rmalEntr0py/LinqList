package CodusMaximus;

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
}