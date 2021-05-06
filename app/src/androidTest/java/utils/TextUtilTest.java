package utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static com.example.go4lunch.goforlunch.utils.TextUtil.convertListToString;
import static com.example.go4lunch.goforlunch.utils.TextUtil.isEmailCorrect;
import static com.example.go4lunch.goforlunch.utils.TextUtil.isTextLongEnough;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


    @RunWith(JUnit4.class)
    public class TextUtilTest {

        @Test
        public void correctEmail_returnTrue() throws Exception{
            String email = "email@email.com";
            assertTrue(isEmailCorrect(email));
        }

        @Test
        public void missingAtEmail_returnFalse() throws Exception{
            String email = "emailemail.com";
            assertFalse(isEmailCorrect(email));
        }

        @Test
        public void missingDomainExt_returnFalse() throws Exception{
            String email = "email@email";
            assertFalse(isEmailCorrect(email));
        }

        @Test
        public void textLongEnough_returnTrue() throws Exception{
            String text = "text";
            assertTrue(isTextLongEnough(text, 3));
        }

        @Test
        public void textTooShort_returnFalse() throws Exception{
            String text = "te";
            assertFalse(isTextLongEnough(text, 3));
        }

        @Test
        public void convertListToString_correct() throws Exception{
            List<String> listString = new ArrayList<>();
            String string1 = "bla";
            String string2 = "Blabla";
            String string3 = "Bla";
            listString.add(string1);
            listString.add(string2);
            listString.add(string3);
            assertEquals(string1 + ", " + string2 + ", " + string3, convertListToString(listString));
        }
    }
