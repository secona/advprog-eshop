package id.ac.ui.cs.advprog.eshop.service;

public interface LogService {
    void log(String level, String message);
    void info(String message);
    void error(String message);
    void debug(String message);
    void warn(String message);
}
