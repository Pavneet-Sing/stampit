package com.pavneetsingh.android.stampit;

import android.arch.persistence.room.Index;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
 public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    private static String genereteRecursiveBackChar(int i) {
        return i < 0 ? "" : genereteRecursiveBackChar((i / 26) - 1) + (char) (65 + i % 26);
    }
static List<String> columnNames(int n)
{
    List<String> result = new ArrayList<String>();
    for (int i = 0; i < n; i++) {
        result.add(genereteRecursiveBackChar(i));
    }
    return result;

}


    @Test
    public void coTest(){
        main(null);
    }

    /**
     * DO NOT MODIFY THIS METHOD!
     */
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter number");
//        int _columns = Integer.parseInt(in.nextLine().trim());
        int _columns = 300;

        List<String> result = columnNames(_columns);

        System.out.println(String.join(", ", result));
    }


}

