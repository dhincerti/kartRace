package com.example.demo;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class DemoTest {

    @Test
    void doSomething() {
        assertThat("", Matchers.emptyString());
    }

    @Test
    void test() {
        List<Integer> a = List.of(1, 2, 3, 4, 5);
        int d = 6;

        int startingPosition = d % a.size();

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            result.add(a.get(startingPosition));
            startingPosition++;

            if (startingPosition == a.size()) {
                startingPosition = startingPosition - a.size();
            }
        }


        System.out.println(result);
    }
}
