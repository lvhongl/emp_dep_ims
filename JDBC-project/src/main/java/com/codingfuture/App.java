package com.codingfuture;

import com.codingfuture.terminal.ImsBaseTerminal;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        new ImsBaseTerminal().console();
    }
}
