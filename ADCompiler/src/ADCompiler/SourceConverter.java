package ADCompiler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ADCompiler
 * Created by josanggyeong on 2016. 9. 11..
 */
class SourceConverter {

    private final List<String> convertedCode = new ArrayList<>();
    private String convertedListId = "";
    private String convertedResultId = "";

    SourceConverter() {
        convertedCode.add("#include<stdio.h>");
        convertedCode.add("\n");
        convertedCode.add("int main(void){");
        convertedCode.add("\n");
    }

    List<String> getConvertedCode() {
        return convertedCode;
    }

    void convertCode(String type, List<String> originalCode) {


        switch (type) {

            case "DEF":

                Pattern listPattern = Pattern.compile("\\[(.*?)\\]");
                Matcher m = listPattern.matcher(originalCode.get(1));
                String convertedList = null;
                while (m.find()) convertedList = m.group(1);
                convertedListId = "_AD_" + originalCode.get(0);
                int listLength = 0;
                if (convertedList != null) {
                    listLength = convertedList.split(", ").length;
                } else {
                    System.err.format("Syntax error\n");
                    System.exit(1);
                }

                convertedCode.add("\n");
                convertedCode.add("\tlong " + convertedListId + "[] = {" + convertedList + "};");
                convertedCode.add("\n");
                convertedCode.add("\tint _AD_list_size = " + listLength + ";");
                convertedCode.add("\n");
                break;

            case "REDUCE":

                String[] originalCodes = originalCode.get(1).split(" ");

                String convertedOperator = originalCodes[0];
                //String convertedInteger = originalCodes[1];
                convertedResultId = "_AD_result";

                convertedCode.add("\tlong " + convertedResultId + " = 0;");
                convertedCode.add("\n\n");
                convertedCode.add("\tint _AD_i = 0;");
                convertedCode.add("\n\n");
                convertedCode.add("\tfor (_AD_i = 0 ; _AD_i < _AD_list_size ; _AD_i++){");
                convertedCode.add("\n");
                convertedCode.add("\t\t" + convertedResultId + " " + convertedOperator + "= " + convertedListId + "[_AD_i];");
                convertedCode.add("\n");
                convertedCode.add("\t}");
                convertedCode.add("\n\n");
                break;

            case "PRINT":

                convertedCode.add("\tprintf(" + '"' + "%ld" + "\\n" + '"' + "," + convertedResultId + ");");
                convertedCode.add("\n\n");
                convertedCode.add("\treturn 0;");
                convertedCode.add("\n\n");
                convertedCode.add("}");
                break;

            default:
                break;

        }

    }


}
