package ADCompiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by josanggyeong on 2016. 9. 11..
 */
class SourceTokenizer {

    SourceTokenizer() {

    }

    private enum Category {

        DEF("def"), REDUCE("reduce"), PRINT("print"), PLUS("+"), MULTIPLY("*"),
        LISTLEFT("["), LISTRIGHT("]"), LPAR("("), RPAR(")"), ID(null), INT(null);

        final private String lexeme;

        Category(String s) {
            lexeme = s;
        }
    }

    private static HashMap<String, Category> tokenMap = new HashMap<String, Category>();

    static {
        for (Category c : Category.values())
            tokenMap.put(c.lexeme, c);
    }

    void tokenize(List<String> sourceCode, SourceConverter sourceConverter) {

        Pattern adPattern = Pattern.compile("\\(\\s*(def|reduce|print)\\s*" +
                "\\s+([a-zA-Z][a-zA-Z0-9]{0,4})" +
                "\\s*(\\[(\\s*[0-9]+\\s*,){0,4}\\s*[0-9]+\\s*\\]|([\\+|\\*])\\s+([0-9]+)\\s+([a-zA-Z][a-zA-Z0-9]{0,4}))?\\s*\\)");

        for (String thisLine : sourceCode) {

            Matcher m = adPattern.matcher(thisLine);

            if (m.matches()) {

                List<String> tmpTokens = new ArrayList<>();

                switch (tokenMap.get(m.group(1))) {
                    case DEF:
                        tmpTokens.add(m.group(2));
                        tmpTokens.add(m.group(3));
                        sourceConverter.convertCode("DEF", tmpTokens);
                        break;

                    case REDUCE:
                        tmpTokens.add(m.group(2));
                        StringTokenizer splicedTokens = new StringTokenizer(m.group(3));
                        while (splicedTokens.hasMoreTokens()) tmpTokens.add(splicedTokens.nextToken());
                        sourceConverter.convertCode("REDUCE", tmpTokens);
                        break;

                    case PRINT:
                        tmpTokens.add(m.group(2));
                        sourceConverter.convertCode("PRINT", tmpTokens);
                        break;

                    default:
                        break;

                }

            } else {
                System.err.format("Syntax error\n");
                System.exit(1);
            }

        }

    }

}