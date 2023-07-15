package com.soob.pokedex;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for formatting names and stuff from responses
 */
public class NameUtils
{
    public static String removeHyphensAndCapitalise(String textToFormat)
    {
        List<String> capitalisedWords = new ArrayList<>();

        // split the text on the hyphen and loop through the text parts to capitalise the first letter
        for (String textPart : textToFormat.split("-"))
        {
            String capitalisedWord = textPart.substring(0, 1).toUpperCase() + textPart.substring(1);
            capitalisedWords.add(capitalisedWord);
        }

        // add the text parts back together with spaces between
        return String.join(" ", capitalisedWords);
    }
}
