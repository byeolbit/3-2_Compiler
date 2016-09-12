package ADCompiler;

/**
 * ADCompiler
 * Created by josanggyeong on 2016. 9. 10..
 *
 * Used OS : Mac OS X 10.11.6
 * Tested C compiler : Clang-703.0.31
 */

public class Main {

    public static void main(String[] args) {

        ADCompiler ADcompiler = new ADCompiler();

        ADcompiler.run(args[0]);

    }
}