package ADCompiler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by josanggyeong on 2016. 9. 12..
 */
class CWriter {

    CWriter() {
    }

    void writeCFile(String sourcePath, List<String> convertedCode) {

        Pattern pathPattern = Pattern.compile("(.*?)\\.ad");
        Matcher m = pathPattern.matcher(sourcePath);
        String filePath = null;

        if (m.find()) {
            filePath = m.group(1) + ".c";
        } else {
            System.err.format("Wrong file path : \n");
            System.exit(1);
        }

        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(filePath, true));

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
