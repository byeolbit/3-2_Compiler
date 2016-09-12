package ADCompiler;

/**
 * Created by josanggyeong on 2016. 9. 10..
 */

class ADCompiler {

    ADCompiler() {

    }

    void run(String sourcePath) {

        SourceReader sourceReader = new SourceReader();
        SourceTokenizer sourceTokenizer = new SourceTokenizer();
        SourceConverter sourceConverter = new SourceConverter();
        CWriter cWriter = new CWriter();

        sourceTokenizer.tokenize(sourceReader.getSourceCode(sourcePath), sourceConverter);
        cWriter.writeCFile(sourcePath, sourceConverter.getConvertedCode());

    }
}
