package mum.swe.mumsched.service;

/**
 * @author Smith T

 * May 10, 2020

 */
public interface SecurityServices {
    String findLoggedInUsername();
    void autologin(String username, String password);
}
