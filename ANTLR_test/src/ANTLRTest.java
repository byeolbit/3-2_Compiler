/**
 * antlr_test
 * Created by sanggyeongjo on 2016. 10. 10..
 */

import org.antlr.v4.runtime.*;

public class ANTLRTest
{
    public static void main(String[] args) throws Exception
    {
        MiniCLexer lexer = new MiniCLexer( new ANTLRFileStream(“test.c”));
        CommonTokenStream tokens = new CommonTokenStream( lexer );
        MiniCParser parser = new MiniCParser( tokens );
        ParseTree tree = parser.program();

    }

}
