package ADCompiler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by josanggyeong on 2016. 9. 10..
 */

class SourceReader {


    SourceReader() {

    }

    List<String> getSourceCode(String sourcePath) {

        List<String> sourceCode = new ArrayList<>();

        try {

            BufferedReader in = new BufferedReader(new FileReader(sourcePath));

            String tempString;

            while ((tempString = in.readLine()) != null) sourceCode.add(tempString);

            in.close();

        } catch (Exception e) {

            System.err.format("File read error : \n %s", e);
            System.exit(1);

        }
        return sourceCode;
    }

}
