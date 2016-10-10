/**
 * MiniC
 * Created by sanggyeongjo on 2016. 10. 10..
 */

grammar MiniC;
program             : decl+                         {System.out.println("201200000 Rule 0");}
                    ;
decl                : var_decl                      {System.out.println("201200000 Rule 1‐1");}
                    | fun_decl                      {System.out.println("201200000 Rule 1‐2");}
                    ;
var_decl            : type_spec IDENT ';'           {System.out.println("201200000 Rule 2‐1");}
                    | type_spec IDENT '[' ']' ';'   {System.out.println("201200000 Rule 2‐2");}
                    ;
