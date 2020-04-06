package edu.social.openid.oauth.google.csrf;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CsrfTokenUtil {

    private static final String CSRF_TOKEN_HEADER_NAME = "csrf-auth-token";
    private static final String CSRF_TOKEN_QUERY_PARAM = "state";

    public static String generate() {
        String upperCaseLetters = RandomStringUtils.random(4, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(4, 97, 122, true, true);
        String numbers = RandomStringUtils.random(2, 48, 57, false, true);
        String combined = upperCaseLetters.concat(lowerCaseLetters).concat(numbers);
        List<Character> characterList = combined.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        Collections.shuffle(characterList);
        String token = characterList.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
        return token;
    }

    public static String getCsrfTokenHeaderName() {
        return CSRF_TOKEN_HEADER_NAME;
    }

    public static String getCsrfTokenQueryParam() {
        return CSRF_TOKEN_QUERY_PARAM;
    }
}
