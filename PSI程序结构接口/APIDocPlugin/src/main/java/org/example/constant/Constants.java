package org.example.constant;

import java.util.HashSet;
import java.util.Set;

public class Constants {

    public static String RequestMapping = "org.springframework.web.bind.annotation.RequestMapping";

    public static String GetMapping = "org.springframework.web.bind.annotation.GetMapping";

    public static String PostMapping = "org.springframework.web.bind.annotation.PostMapping";

    public static String PutMapping = "org.springframework.web.bind.annotation.PutMapping";

    public static String DeleteMapping = "org.springframework.web.bind.annotation.DeleteMapping";

    public static String PatchMapping = "org.springframework.web.bind.annotation.PatchMapping";

    public static String RequestBody = "org.springframework.web.bind.annotation.RequestBody";

    public static String RequestParam = "org.springframework.web.bind.annotation.RequestParam";

    public static String RequestHeader = "org.springframework.web.bind.annotation.RequestHeader";

    public static String RequestAttribute = "org.springframework.web.bind.annotation.RequestAttribute";

    public static String PathVariable = "org.springframework.web.bind.annotation.PathVariable";

    public static Set<String> basicType = new HashSet<>();

    static {
        basicType.add("int");
        basicType.add("boolean");
        basicType.add("byte");
        basicType.add("short");
        basicType.add("long");
        basicType.add("float");
        basicType.add("double");
        basicType.add("char");
        basicType.add("Boolean");
        basicType.add("Byte");
        basicType.add("Short");
        basicType.add("Integer");
        basicType.add("Long");
        basicType.add("Float");
        basicType.add("Double");
        basicType.add("String");
        basicType.add("Date");
        basicType.add("BigDecimal");
        basicType.add("Map");
        basicType.add("HashMap");
    }
}
