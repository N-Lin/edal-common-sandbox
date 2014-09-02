package uk.ac.rdg.resc.edal.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ValuesArray2DTest {

    private ValuesArray2D data;

    private static final int XSIZE = 5;
    private static final int YSIZE = 7;

    @Before
    public void setUp() throws Exception {
        data = new ValuesArray2D(YSIZE, XSIZE);
        for (int i = 0; i < YSIZE; i++) {
            for (int j = 0; j < XSIZE; j++) {
                data.set(1 * i + j, i, j);
            }
        }
    }

    @Test
    public void testIterator() {
        List<Number> expected = new ArrayList<Number>();
        for (int i = 0; i < YSIZE; i++) {
            for (int j = 0; j < XSIZE; j++) {
                expected.add(1 * i + j);
            }
        }

        Iterator<Number> iterator = data.iterator();
        int index = 0;
        while (iterator.hasNext() && index < data.size()) {
            assertEquals(expected.get(index), iterator.next());
            index++;
        }
    }

    @Test
    public void test() {
        float f = 12.0f;
        data.set(f, 2, 3);
        assertEquals(data.get(2, 3).doubleValue(), f, 1e-6);

        long longInt = 123456789999999999L;
        data.set(longInt, 1, 3);
        assertEquals(data.get(1, 3).longValue(), longInt);

        int i = 5;
        data.set(i, 2, 2);
        assertEquals(data.get(2, 2).longValue(), i);

        short s = (short) 100000;
        data.set(s, 1, 2);
        assertEquals(data.get(1, 2), s);

        byte b = 120;
        data.set(b, 1, 4);
        data.get(1, 4).equals(b);
        new Long(4).doubleValue();
        new Double(5.6).longValue();
        assertEquals(data.get(1, 4), new Byte(b));

        double d = 129.9998;
        data.set(d, 2, 4);
        assertEquals(data.get(2, 4).doubleValue(), d, 1e-6);

        data.set(null, 6, 4);
        assertEquals(data.get(6, 4), null);

    }
}
