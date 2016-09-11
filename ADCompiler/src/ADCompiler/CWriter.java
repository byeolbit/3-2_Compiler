package ADCompiler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

/**
 * Created by josanggyeong on 2016. 9. 12..
 */
class CWriter {

    CWriter() {
    }

    void writeCFile(List<String> convertedCode) {

        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter("./test.c", true));

            for (String aConvertedCode : convertedCode) {
                fw.write(aConvertedCode);
                fw.flush();
            }

            fw.close();

        } catch (Exception e) {

            System.err.format("File write error : \n %s", e);
            System.exit(1);

        }

    }
}
