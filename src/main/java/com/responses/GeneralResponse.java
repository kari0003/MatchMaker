package com.responses;

import java.util.LinkedList;

/**
 * Created by kari0003 on 2016.07.17..
 */

public class GeneralResponse {
    public Object data;
    public LinkedList<Object> warnings;

    public GeneralResponse(Object o) {
        data = o;
    }

    public void addWaring(Object warning) {
        warnings.push(warning);
    }
}
