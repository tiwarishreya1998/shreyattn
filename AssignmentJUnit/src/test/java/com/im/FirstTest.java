package com.im;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FirstTest {
    static First first;
    @BeforeAll
    static void creationFirstClassObject() {
        first = new First();
    }

    @Nested
    class ToReplaceSubString{
        @Test
        void shouldReturnNewString_When_SubstringIsFound(){
            //given
            String mainString="my name is";
            String findString="my";
            String replacedString="his";
            String expectedString="his name is";

            //when

            String calculateString= first.replaceSubString(mainString,findString,replacedString);
            System.out.println(calculateString);
            //then
            assertEquals(expectedString,calculateString);
        }
        @Test
        void shouldReturnOriginalString_When_SubstringIsNotFound() {
            //given
            String mainString = "my name is";
            String findString = "mi";
            String replacedString ="my";
            String expectedString = "my name is";

            //when
            String calculateString = first.replaceSubString(mainString, findString, replacedString);

            //then
            assertEquals(expectedString, calculateString);

        }
        @Test
        void should_return_originalString_when_given_null()
        {
            String originalString = "my name is";
            String substringToBeReplaced = null;
            String newSubstring = "qwe";
            String expected ="my name is";

            //when
            String string = (first.replaceSubString(originalString,substringToBeReplaced,newSubstring));

            //then
            assertEquals(expected,string);
        }
        @Test
        void should_return_originalString_when_given_null1()
        {
            //given
            String originalString = "my name is";
            String substringToBeReplaced = "abc";
            String newSubstring = null;
            String expected ="my name is";

            //when
            String string = (first.replaceSubString(originalString,substringToBeReplaced,newSubstring));

            //then
            assertEquals(expected,string);
        }

    }

    @Nested
    class ToFilterElements{

        @Test
        void shouldReturnOddElementOnly_When_OddElementExist_AfterFilterEvenElement() {
            //given
            List<Integer> list = new ArrayList<>();
            for (int i = 1; i < 6; i++) {
                list.add(i);
            }
            List<Integer> expectedlist = new ArrayList<>();
            expectedlist.add(1);
            expectedlist.add(3);
            expectedlist.add(5);

            //when
            List calculatelist = first.filterEvenElements(list);

            //then
            assertEquals(expectedlist, calculatelist);

        }

    }
    @Nested
    class ToCalculateAverage
    {
        @Test
        void shouldThrowMessageInvalidInput_When_ListIsNotExist() {
            //given
            List<BigDecimal> list = null;
            //List<BigDecimal> list=new ArrayList<>();

            //when
            try {
                first.calculateAverage(list);
            }

            //then
            catch (RuntimeException r) {
                System.out.println(r);

            }
        }

        @Test
        void shouldThrowMessageInvalidInput_When_ListIsEmpty() {
            //given
            List<BigDecimal> list = new ArrayList<>();

            //when
            try {
                first.calculateAverage(list);
            }

            //then
            catch (RuntimeException r) {
                System.out.println(r);

            }
        }

        @Test
        void shouldReturnAverageValue_When_ListContainsElement() {

            //given
            List<BigDecimal> list = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                list.add(new BigDecimal(1212121));
                list.add(new BigDecimal(1212121));
                list.add(new BigDecimal(1212121));
                list.add(new BigDecimal(1212121));
            }
            BigDecimal expectedAverage = new BigDecimal(1212121);

            //when
            BigDecimal calculateAverage=first.calculateAverage(list);

            //then
            assertEquals(expectedAverage, calculateAverage);
        }

    }

    @Nested
    class ToCheckStringIsPalindrome
    {
        @Test
        void shouldReturnTrue_When_StringIsPalindrome() {
            //given
            String originalInput = "madam";

            //when
            boolean palindromeCheck = first.isPallindrome(originalInput);

            //then
            assertTrue(palindromeCheck);

        }
    }

}