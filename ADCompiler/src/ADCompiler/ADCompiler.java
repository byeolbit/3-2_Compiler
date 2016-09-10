package ADCompiler;

import java.io.IOException;

/**
 * Created by josanggyeong on 2016. 9. 10..
 */

class ADCompiler {

    ADCompiler(){

    }

    void run() throws IOException {

        SourceReader sourceReader = new SourceReader();
        SourceTokenizer sourceTokenizer = new SourceTokenizer();
        Lexeme lexeme = new Lexeme();

        lexeme.analyze(sourceTokenizer.tokenize(sourceReader.getSourceCode()));

    }
}
