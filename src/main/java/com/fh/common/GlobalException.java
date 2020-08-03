package com.fh.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
   /* public static void main(String[] args) {
        int[] arr={3,1,55,23,11,6};
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = 0; j < arr.length-i-1; j++) {
                if(arr[j]>arr[j+1]){
                    int temp = arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }

            }
        }
        for (int j = 0; j < arr.length; j++) {
            System.out.println(arr[j]);

        }
    }*/

    @ExceptionHandler(MyException.class)
    public ServerResponse HandlerMyException (MyException e){
        e.printStackTrace();
        return ServerResponse.error(e.getMessage());
    }

    @ExceptionHandler(LoginException.class)
    public ServerResponse HandlerMyLoginException (LoginException e){
        //e.printStackTrace();
        return ServerResponse.error_login();
    }

    @ExceptionHandler(Exception.class)
    public ServerResponse HandlerException (Exception e){
        e.printStackTrace();
        return ServerResponse.error();
    }

}
