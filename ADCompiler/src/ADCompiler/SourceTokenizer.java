package ADCompiler;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by josanggyeong on 2016. 9. 11..
 */
class SourceTokenizer {

    SourceTokenizer() {

    }

    List<String> tokenize(List<String> sourceCode) {

        List<String> tokens = new ArrayList<>();

        for (String thisLine : sourceCode) {

            StringTokenizer thisToken = new StringTokenizer(thisLine);

            while (thisToken.hasMoreTokens()) tokens.add(thisToken.nextToken());

        }

        return tokens;

    }

}