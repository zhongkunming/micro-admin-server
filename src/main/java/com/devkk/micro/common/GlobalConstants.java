package com.devkk.micro.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongkunming
 */
public interface GlobalConstants {

    String SQL_ONLY_ONE = "limit 1";

    interface BooleanDict {

        Integer TRUE = 1;

        Integer FALSE = 0;
    }

    interface MenuType {

        String DIRECTORY = "D";

        String MENU = "M";

        String BUTTON = "B";
    }

    interface MenuLayout {

        String DEFAULT = "";
    }

    interface DictType {

        String SIMPLE = "simple";

        String OPTION = "option";
    }

    // -----------------------------------------------------------------------------------------------------------------

    interface SortNum {

        Integer DEFAULT = 9999;
    }

    abstract class ImmutableDict {

        final static String GENDER = "gender";

        public static List<String> LIST = new ArrayList<>();
    }
}
